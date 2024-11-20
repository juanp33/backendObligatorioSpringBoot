package com.example.demo.Services;

import com.example.demo.Modelos.Lobby;
import com.example.demo.Repositorios.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LobbyService {

    @Autowired
    private LobbyRepository lobbyRepository; // Repositorio para gestionar la persistencia de lobbies
    @Autowired
    private CompetitivoService competitivoService;

    public Lobby crearLobby(String lobbyId, String jugador) {
        Lobby lobby = new Lobby(lobbyId);
        lobby.agregarJugador(jugador); // Añadir al jugador que crea el lobby
        lobby.setTurnoActual(jugador); // Configurar el turno inicial al creador
        return lobbyRepository.save(lobby); // Guardar en la base de datos
    }

    public String getFirstPlayer(String lobbyId) {
        Optional<Lobby> lobby = lobbyRepository.findById(lobbyId);
        if (lobby.isPresent() && !lobby.get().getJugadores().isEmpty()) {
            return lobby.get().getJugadores().get(0);
        }
        return null;
    }

    public String removerJugador(String lobbyId, String jugador) {
        Lobby lobby = lobbyRepository.findById(lobbyId).orElse(null);
        if (lobby != null) {
            lobby.removerJugador(jugador);
            if (lobby.getJugadores().isEmpty()) {
                lobbyRepository.delete(lobby); // Eliminar el lobby si no tiene jugadores
            } else {
                lobbyRepository.save(lobby); // Guardar los cambios
            }
            return "Jugador removido correctamente";
        }
        return "Lobby no encontrado";
    }
    public String unirJugador(String lobbyId, String jugador) {
        Optional<Lobby> lobbyOptional = lobbyRepository.findById(lobbyId);
        if (lobbyOptional.isPresent()) {
            Lobby lobby = lobbyOptional.get();
            if (!lobby.estaCompleto()) {
                lobby.agregarJugador(jugador);
                lobbyRepository.save(lobby); // Actualizar el lobby en la base de datos
                return "Jugador añadido correctamente";
            } else {
                return "Lobby completo. No se pueden unir más jugadores";
            }
        }
        return "Lobby no encontrado";
    }

    public Lobby obtenerLobby(String lobbyId) {
        return lobbyRepository.findById(lobbyId).orElse(null);
    }

    public List<Lobby> obtenerLobbiesActivos() {
        return lobbyRepository.findAll().stream()
                .filter(lobby -> lobby.getJugadores().size() < 2) // Considerar activos los lobbies con menos de 2 jugadores
                .collect(Collectors.toList());
    }

    public void eliminarLobby(String lobbyId) {
        lobbyRepository.deleteById(lobbyId);
    }

    public boolean esLobbyActivo(String lobbyId) {
        Optional<Lobby> lobby = lobbyRepository.findById(lobbyId);

        return lobby.isPresent() && lobby.get().getJugadores().size() == 1 ;
    }
}
