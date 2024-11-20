package com.example.demo.Controllers;

import com.example.demo.Modelos.Lobby;
import com.example.demo.Services.LobbyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/lobbies")
public class LobbyController {

    @Autowired
    private LobbyService lobbyService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/lobby/{lobbyId}")
    @SendTo("/topic/lobbies/{lobbyId}")
    public List<String> joinLobby(@DestinationVariable String lobbyId, @Payload String message) {
        String jugador = parseJugadorFromMessage(message);
        lobbyService.unirJugador(lobbyId, jugador);
        Lobby lobby = lobbyService.obtenerLobby(lobbyId);

        if (lobby != null) {
            return lobby.getJugadores();
        }
        return List.of();
    }
    @PostMapping("/crear")
    public String crearLobby(@RequestParam String lobbyId, @RequestParam String jugador) {
        lobbyService.crearLobby(lobbyId, jugador);
        return "Lobby creado con éxito";
    }

    @PostMapping("/unir")
    public String unirJugador(@RequestParam String lobbyId, @RequestParam String jugador) {
        String mensaje = lobbyService.unirJugador(lobbyId, jugador);
        actualizarEstadoLobby(lobbyId);
        return mensaje;
    }

    @PostMapping("/remover")
    public String removerJugador(@RequestParam String lobbyId, @RequestParam String jugador) {
        String mensaje = lobbyService.removerJugador(lobbyId, jugador);
        actualizarEstadoLobby(lobbyId);
        return mensaje;
    }
    @MessageMapping("/leave/{lobbyId}")
    @SendTo("/topic/lobbies/{lobbyId}")
    public List<String> leaveLobby(@DestinationVariable String lobbyId, @Payload String message) {
        System.out.println("Entro a leavelobby");
        String jugador = parseJugadorFromMessage(message);
        lobbyService.removerJugador(lobbyId, jugador);
        Lobby lobby = lobbyService.obtenerLobby(lobbyId);

        if (lobby != null) {
            List<String> jugadoresRestantes = lobby.getJugadores();
            messagingTemplate.convertAndSend("/topic/lobbies/" + lobbyId, jugadoresRestantes);
            return jugadoresRestantes;
        }

        return List.of();
    }
    @GetMapping("/activos")
    public List<Lobby> obtenerLobbiesActivos() {
        return lobbyService.obtenerLobbiesActivos();
    }
    @MessageMapping("/chat/{lobbyId}")
    @SendTo("/topic/chat/{lobbyId}")
    public void sendMessage(@DestinationVariable String lobbyId, @Payload String message) {
        // Difundir el mensaje a todos los suscriptores del lobby específico
        messagingTemplate.convertAndSend("/topic/chat/" + lobbyId, message);
    }
    @GetMapping("/{lobbyId}/jugadores")
    public List<String> obtenerJugadores(@PathVariable String lobbyId) {
        Lobby lobby = lobbyService.obtenerLobby(lobbyId);
        return lobby != null ? lobby.getJugadores() : new ArrayList<>();
    }

    @PostMapping("/startgame")
    public List<String> startGame(@RequestParam String lobbyId) {
        Lobby lobby = lobbyService.obtenerLobby(lobbyId);
        if (lobby != null && lobby.estaCompleto()) {
            messagingTemplate.convertAndSend("/topic/lobby/" + lobbyId, "start");
            return lobby.getJugadores();
        }
        return List.of(); // Devuelve una lista vacía si el lobby no está listo
    }

    // Método privado para actualizar el estado del lobby y enviar una notificación
    private void actualizarEstadoLobby(String lobbyId) {
        Lobby lobby = lobbyService.obtenerLobby(lobbyId);
        if (lobby != null) {
            // Notificar a los jugadores del estado actualizado del lobby
            messagingTemplate.convertAndSend("/topic/lobby/" + lobbyId, lobby.getJugadores());
        }
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
