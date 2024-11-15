package com.example.demo.Services;

import com.example.demo.Modelos.Lobby;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LobbyService {
    private final Map<String, Lobby> lobbies = new ConcurrentHashMap<>();

    public Lobby crearLobby(String lobbyId, String jugador) {
        Lobby lobby = new Lobby(lobbyId);
        lobby.agregarJugador(jugador); // Añadir al jugador que crea el lobby
        lobby.setTurnoActual(jugador); // Configurar el turno inicial al creador
        lobbies.put(lobbyId, lobby);
        return lobby;
    }
    public String getFirstPlayer(String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null && !lobby.getJugadores().isEmpty()) {

            return lobby.getJugadores().get(0);
        }
        return null;
    }
    public String unirJugador(String lobbyId, String jugador) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            if (!lobby.estaCompleto()) {
                lobby.agregarJugador(jugador);
                System.out.println(lobby.getJugadores());
                return "Jugador añadido correctamente";
            } else {
                return "Lobby completo. No se pueden unir más jugadores";
            }
        }
        return "Lobby no encontrado";
    }

    public Lobby obtenerLobby(String lobbyId) {
        return lobbies.get(lobbyId);
    }
    public List<Lobby> obtenerLobbiesActivos() {
        List<Lobby> lobbiesActivos = new ArrayList<>();
        for (Lobby lobby : lobbies.values()) {

                lobbiesActivos.add(lobby);

        }
        return lobbiesActivos;
    }
    public String cambiarTurno(String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null && lobby.estaCompleto()) {
            String jugadorActual = lobby.getTurnoActual();
            String nuevoTurno = jugadorActual.equals(lobby.getJugadores().get(0))
                    ? lobby.getJugadores().get(1)
                    : lobby.getJugadores().get(0);
            lobby.setTurnoActual(nuevoTurno);
            return nuevoTurno;
        }
        return null;
    }

    public boolean esLobbyActivo(String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        return lobby != null && lobby.isActivo();
    }
}