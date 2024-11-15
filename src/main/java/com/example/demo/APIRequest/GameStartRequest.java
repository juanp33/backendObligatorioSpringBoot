package com.example.demo.APIRequest;

import java.util.ArrayList;
import java.util.List;

public class GameStartRequest {
    private String lobbyId;
    private ArrayList<String> jugadores; // Lista que contendrá los jugadores, en este caso strings con los nombres o IDs
    private String turno;

    // Constructor vacío (requerido para la deserialización)
    public GameStartRequest() {}

    // Getters y Setters
    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public List<String> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<String> jugadores) {
        this.jugadores = jugadores;
    }
}