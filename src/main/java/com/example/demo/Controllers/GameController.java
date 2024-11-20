package com.example.demo.Controllers;

import com.example.demo.APIRequest.GameFinishRequest;
import com.example.demo.APIRequest.GameStartRequest;
import com.example.demo.APIRequest.PreguntaRequest;
import com.example.demo.Modelos.Competitivo;
import com.example.demo.Modelos.Jugador;
import com.example.demo.Modelos.Lobby;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private OpenAIService openAIService;

    // Unirse al lobby


    // Crear pregunta
    @MessageMapping("/pregunta/{lobbyId}")
    @SendTo("/topic/lobbies/{lobbyId}")
    public PreguntaRequest crearPregunta(@DestinationVariable String lobbyId, @Payload String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        GameStartRequest request;
        try {
            request = objectMapper.readValue(message, GameStartRequest.class);
            String turno = request.getTurno();
            List<String> jugadores = request.getJugadores();
            String jugadorActivo = jugadores.get(0).equals(turno) ? jugadores.get(1) : jugadores.get(0);

            String categoria = ruletaController.getRandomCategoria();
            Pregunta pregunta = OpenAIService.generarPregunta(categoria);

            return new PreguntaRequest(pregunta, turno, categoria);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al deserializar el mensaje JSON");
        }
    }

    // Respuesta correcta
    @MessageMapping("/respuestaCorrecta/{lobbyId}")
    @SendTo("/topic/lobbies/{lobbyId}")
    public String recibirRespuestaMarcada(@DestinationVariable String lobbyId, @Payload String message) {
        return message;
    }

    // Respuesta
    @MessageMapping("/respuesta/{lobbyId}")
    @SendTo("/topic/lobbies/{lobbyId}")
    public PreguntaRequest recibirRespuesta(@DestinationVariable String lobbyId, @Payload String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        GameStartRequest request;
        try {
            request = objectMapper.readValue(message, GameStartRequest.class);
            String turno = request.getTurno();
            List<String> jugadores = request.getJugadores();
            String jugadorActivo = jugadores.get(0).equals(turno) ? jugadores.get(1) : jugadores.get(0);

            String categoria = ruletaController.getRandomCategoria();
            Pregunta pregunta = OpenAIService.generarPregunta(categoria);

            return new PreguntaRequest(pregunta, turno, categoria);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al deserializar el mensaje JSON");
        }
    }

    // Comenzar juego
    @MessageMapping("/start/{lobbyId}")
    @SendTo("/topic/lobbies/{lobbyId}")
    public Map<String, String> startGame(@DestinationVariable String lobbyId, @Payload String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        GameStartRequest request;
        String categoria = ruletaController.getRandomCategoria();
        try {
            request = objectMapper.readValue(message, GameStartRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al deserializar el mensaje JSON");
        }

        Lobby lobby = lobbyService.obtenerLobby(lobbyId);
        if (lobby != null && lobby.estaCompleto()) {
            List<String> jugadores = lobby.getJugadores();
            Jugador jugador1 = jugadorService.encontrarPorNombre(jugadores.get(0)).orElse(null);
            Jugador jugador2 = jugadorService.encontrarPorNombre(jugadores.get(1)).orElse(null);

            if (jugador1 != null && jugador2 != null) {
                Competitivo competitivo = new Competitivo(jugador1, jugador2, lobbyId, "En juego");
                competitivoService.guardarPartidaCompetitiva(competitivo);
            }

            Map<String, String> response = new HashMap<>();
            response.put("tipo", "START");
            response.put("mensaje", "El juego ha comenzado en el lobby: " + lobbyId);
            response.put("turno", request.getJugadores().get(0));

            return response;
        }
        return Map.of();
    }

    // Dejar el lobby


    // Finalizar partida
    @MessageMapping("/finalizarPartida/{lobbyId}")
    public void finalizarPartida(@DestinationVariable String lobbyId, @Payload String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        GameFinishRequest request;
        Competitivo competitivo = competitivoService.encontrarCompetitivoPorLobbyId(lobbyId);
        try {
            request = objectMapper.readValue(message, GameFinishRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        List<Integer> puntajes = request.getPuntajeJugadores();
        int puntosJugador1 = puntajes.get(0);
        int puntosJugador2 = puntajes.get(1);

        competitivoService.finalizarPartidaCompetitiva(puntosJugador1, puntosJugador2, lobbyId, "Finalizada");
        Jugador jugador1 = competitivo.getJugador1();
        Jugador jugador2 = competitivo.getJugador2();
        if (puntosJugador1 > puntosJugador2) {
            jugadorService.actualizarPartidaGanada(jugador1, jugador2, jugador1);
        } else {
            jugadorService.actualizarPartidaGanada(jugador1, jugador2, jugador2);
        }
        lobbyService.eliminarLobby(lobbyId);
    }

    private String parseJugadorFromMessage(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            return jsonNode.get("jugador").asText();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}