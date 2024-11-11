package com.example.demo.Controllers;

import com.example.demo.APIRequest.GameStartRequest;
import com.example.demo.Modelos.Competitivo;
import com.example.demo.Modelos.Jugador;
import com.example.demo.Services.CompetitivoService;
import com.example.demo.Services.JugadorService;
import com.example.demo.Services.LobbyService;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class GameController {
@Autowired
    private LobbyService lobbyService;
    private CompetitivoService competitivoService;
    private JugadorService jugadorService;

    private ConcurrentHashMap<String, List<String>> lobbies = new ConcurrentHashMap<>();
    @Autowired
    private SimpMessagingTemplate messagingTemplate; // Inyectamos el SimpMessagingTemplate


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

    @MessageMapping("/start/{lobbyId}")
    @SendTo("/topic/lobbies/{lobbyId}")
    public Map<String, String> startGame(@DestinationVariable String lobbyId, @Payload String message) {
        // Deserializa el mensaje JSON en un objeto con los jugadores
        // Aquí asumimos que message es un JSON string que contiene los jugadores
        ObjectMapper objectMapper = new ObjectMapper();
        GameStartRequest request;

        try {
            request = objectMapper.readValue(message, GameStartRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al deserializar el mensaje JSON");
        }

        // Crear un objeto Competitivo y asignar los jugadores
        Jugador jugador1= (jugadorService.encontrarPorNombre(request.getJugadores().get(0))).get();
        Jugador jugador2= (jugadorService.encontrarPorNombre(request.getJugadores().get(1))).get();
        Competitivo competitivo= new Competitivo(jugador1, jugador2);
        competitivoService.guardarPartidaCompetitiva(competitivo);
        // Crear la respuesta
        Map<String, String> response = new HashMap<>();
        response.put("tipo", "START");
        response.put("mensaje", "El juego ha comenzado en el lobby: " + lobbyId);
        response.put("turno", request.getJugadores().get(0));

        return response;
    }
    @MessageMapping("/pregunta/{lobbyId}")
    @SendTo("/topic/lobbies/{lobbyId}")
    public Map<String, String> MandarPregunta(@DestinationVariable String lobbyId, @Payload String message) {
        // Deserializa el mensaje JSON en un objeto con los jugadores
        // Aquí asumimos que message es un JSON string que contiene los jugadores
        ObjectMapper objectMapper = new ObjectMapper();
        GameStartRequest request;

        try {
            request = objectMapper.readValue(message, GameStartRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al deserializar el mensaje JSON");
        }

        // Crear un objeto Competitivo y asignar los jugadores
        Jugador jugador1= (jugadorService.encontrarPorNombre(request.getJugadores().get(0))).get();
        Jugador jugador2= (jugadorService.encontrarPorNombre(request.getJugadores().get(1))).get();
        Competitivo competitivo= new Competitivo(jugador1, jugador2);
        competitivoService.guardarPartidaCompetitiva(competitivo);
        // Crear la respuesta
        Map<String, String> response = new HashMap<>();
        response.put("tipo", "START");
        response.put("mensaje", "El juego ha comenzado en el lobby: " + lobbyId);
        response.put("turno", request.getJugadores().get(0));

        return response;
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

    private String parseLobbyIdFromMessage(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convertir el mensaje JSON a un objeto JsonNode
            JsonNode jsonNode = objectMapper.readTree(message);
            // Extraer el lobbyId desde el campo "lobbyId"
            return jsonNode.get("lobbyId").asText();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Manejar el error según sea necesario
        }
    }
}
