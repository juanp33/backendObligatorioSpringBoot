package com.example.demo.Controllers;

import com.example.demo.APIRequest.GameFinishRequest;
import com.example.demo.APIRequest.GameStartRequest;
import com.example.demo.APIRequest.PreguntaRequest;
import com.example.demo.Modelos.Competitivo;
import com.example.demo.Modelos.Jugador;
import com.example.demo.Modelos.Pregunta;
import com.example.demo.Services.CompetitivoService;
import com.example.demo.Services.JugadorService;
import com.example.demo.Services.LobbyService;
import com.example.demo.Services.OpenAIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class GameController {
@Autowired
    private LobbyService lobbyService;
    @Autowired
    private CompetitivoService competitivoService;
    @Autowired
    private JugadorService jugadorService;
    @Autowired
    private RuletaController ruletaController;

    private ConcurrentHashMap<String, List<String>> lobbies = new ConcurrentHashMap<>();

    private OpenAIService openAIService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/lobby/{lobbyId}")
    @SendTo("/topic/lobbies/{lobbyId}")
    public List<String> joinLobby(@DestinationVariable String lobbyId, String message) {
        String jugador = parseJugadorFromMessage(message);
        lobbies.putIfAbsent(lobbyId, new ArrayList<>());
        List<String> jugadores = lobbies.get(lobbyId);
        if (!jugadores.contains(jugador)) {
            jugadores.add(jugador);
        }


        return jugadores;
    }
    @MessageMapping("/pregunta/{lobbyId}")
    @SendTo("/topic/lobbies/{lobbyId}")
    public PreguntaRequest crearPregunta(@DestinationVariable String lobbyId, @Payload String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        GameStartRequest request;


        try {

            request = objectMapper.readValue(message, GameStartRequest.class);


            String jugadorActivo;
            String turno = request.getTurno();
            List<String> jugadores = request.getJugadores();

            if (jugadores.get(0).equals(turno)) {
                jugadorActivo = jugadores.get(1);
            } else {
                jugadorActivo = jugadores.get(0);
            }

            String categoria = ruletaController.getRandomCategoria();
            Pregunta pregunta = OpenAIService.generarPregunta(categoria);

            System.out.println(categoria);
            System.out.println("XdXD");
            return new PreguntaRequest(pregunta,turno,categoria);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al deserializar el mensaje JSON");
        }

    }
    @MessageMapping("/respuestaCorrecta/{lobbyId}")
    @SendTo("/topic/lobbies/{lobbyId}")
    public String RecibirRespuestaMarcada(@DestinationVariable String lobbyId, @Payload String message) {
        return message;
    }

    @MessageMapping("/respuesta")
    @SendTo("/topic/lobbies/{lobbyId}")
    public PreguntaRequest RecibirRespuesta(@DestinationVariable String lobbyId, @Payload String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        GameStartRequest request;
        try {

            request = objectMapper.readValue(message, GameStartRequest.class);


            String jugadorActivo;
            String turno = request.getTurno();
            List<String> jugadores = request.getJugadores();

            if (jugadores.get(0).equals(turno)) {
                jugadorActivo = jugadores.get(1);
            } else {
                jugadorActivo = jugadores.get(0);
            }

            String categoria = ruletaController.getRandomCategoria();
            Pregunta pregunta = OpenAIService.generarPregunta(categoria);



            return new PreguntaRequest(pregunta,turno,categoria);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al deserializar el mensaje JSON");
        }

    }
    @MessageMapping("/start/{lobbyId}")
    @SendTo("/topic/lobbies/{lobbyId}")
    public Map<String, String> startGame(@DestinationVariable String lobbyId, @Payload String message) {

        ObjectMapper objectMapper = new ObjectMapper();
        GameStartRequest request;
        String categoria = ruletaController.getRandomCategoria();
        System.out.println(message);
        try {
            request = objectMapper.readValue(message, GameStartRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al deserializar el mensaje JSON");
        }

        List<String> jugadores = (lobbyService.obtenerLobby(lobbyId)).getJugadores();
        Jugador jugador1= (jugadorService.encontrarPorNombre(jugadores.get(0)).get());
        Jugador jugador2= (jugadorService.encontrarPorNombre(jugadores.get(1)).get());
        Competitivo competitivo= new Competitivo(jugador1, jugador2, lobbyId);
        System.out.println(lobbyId);
        System.out.println("xdxdxd");
        competitivoService.guardarPartidaCompetitiva(competitivo);

        Map<String, String> response = new HashMap<>();
        response.put("tipo", "START");
        response.put("mensaje", "El juego ha comenzado en el lobby: " + lobbyId);
        response.put("turno", request.getJugadores().get(0));

        return response;
    }

    @MessageMapping("/finalizarPartida/{lobbyId}")

    public void  finalizarPartida(@DestinationVariable String lobbyId,@Payload String message){
        ObjectMapper objectMapper = new ObjectMapper();
        GameFinishRequest request;
        Competitivo competitivo=competitivoService.encontrarCompetitivoPorLobbyId(lobbyId);
        try {
            request = objectMapper.readValue(message, GameFinishRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        List<Integer> Puntajes = request.getPuntajeJugadores();
        int puntosJugador1= Puntajes.get(0);
        int puntosJugador2= Puntajes.get(1);
        System.out.println(puntosJugador1);
        System.out.println(puntosJugador2);
        competitivoService.actualizarPuntajes(puntosJugador1,puntosJugador2,lobbyId);
        Jugador jugador1=competitivo.getJugador1();
        Jugador jugador2=competitivo.getJugador2();
        if(puntosJugador1>puntosJugador2) {
            jugadorService.actualizarPartidaGanada(jugador1,jugador2,jugador1);
        }else{
            jugadorService.actualizarPartidaGanada(jugador1,jugador2,jugador2);
        }

    }

    private String parseJugadorFromMessage(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convertir el mensaje JSON a un objeto JsonNode
            JsonNode jsonNode = objectMapper.readTree(message);
            // Extraer el nombre del jugador desde el campo "jugador"
            return jsonNode.get("jugador").asText();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Manejar el error según sea necesario
        }
    }

}
