package com.example.demo.APIRequest;

import java.util.List;

public class GameStartRequest {
    private String lobbyId;
    private List<String> jugadores; // Lista que contendrá los jugadores, en este caso strings con los nombres o IDs

    // Constructor vacío (requerido para la deserialización)
    public GameStartRequest() {}

    // Getters y Setters
    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public List<String> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<String> jugadores) {
        this.jugadores = jugadores;
    }
}