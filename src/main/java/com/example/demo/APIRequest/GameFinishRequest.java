package com.example.demo.APIRequest;

import java.util.ArrayList;

public class GameFinishRequest {
    private String lobbyId;
    private ArrayList<Integer> puntajeJugadores;


    public GameFinishRequest() {}



    public String getLobbyId() {
        return lobbyId;
    }

    public ArrayList<Integer> getPuntajeJugadores() {
        return puntajeJugadores;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public void setPuntajeJugadores(ArrayList<Integer> puntajeJugadores) {
        this.puntajeJugadores = puntajeJugadores;
    }
}
