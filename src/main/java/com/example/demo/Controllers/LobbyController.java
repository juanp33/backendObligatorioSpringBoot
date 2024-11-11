package com.example.demo.Controllers;

import com.example.demo.Modelos.Lobby;
import com.example.demo.Services.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/lobbies")
public class LobbyController {

    @Autowired
    private LobbyService lobbyService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @PostMapping("/crear")
    public String crearLobby(@RequestParam String lobbyId, @RequestParam String jugador) {
        lobbyService.crearLobby(lobbyId, jugador);
        return "Lobby creado con éxito";
    }

    @PostMapping("/unir")
    public String unirJugador(@RequestParam String lobbyId, @RequestParam String jugador) {
        return lobbyService.unirJugador(lobbyId, jugador);
    }

    @GetMapping("/activos")
    public List<Lobby> obtenerLobbiesActivos() {
        return lobbyService.obtenerLobbiesActivos();
    }

    @GetMapping("/turnoActual")
    public String obtenerTurnoActual(@RequestParam String lobbyId) {
        Lobby lobby = lobbyService.obtenerLobby(lobbyId);
        if (lobby != null && lobby.isActivo()) {
            return lobby.getTurnoActual();
        }
        return "Lobby no activo o no encontrado";
    }

    @MessageMapping("/cambiarTurno")
    @SendTo("/topic/turno")
    public String cambiarTurno(String lobbyId) {
        return lobbyService.cambiarTurno(lobbyId);
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
            return lobby.getJugadores(); // Devuelve los jugadores que están en el lobby
        }
        return List.of(); // Devuelve una lista vacía si el lobby no está listo
    }
}