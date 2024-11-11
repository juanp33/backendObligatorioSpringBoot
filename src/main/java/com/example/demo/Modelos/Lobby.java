package com.example.demo.Modelos;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private String id;
    private List<String> jugadores;
    private String turnoActual;
    private boolean activo; // Campo para indicar si el lobby está activo

    public Lobby(String id) {
        this.id = id;
        this.jugadores = new ArrayList<>();
        this.turnoActual = null;
        this.activo = false; // Inicialmente el lobby no está activo
    }

    public String getId() {
        return id;
    }

    public List<String> getJugadores() {
        return jugadores;
    }

    public String getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(String turnoActual) {
        this.turnoActual = turnoActual;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void agregarJugador(String jugador) {
        if (jugadores.size() < 2) { // Permitir un máximo de 2 jugadores
            jugadores.add(jugador);
            if (jugadores.size() == 2) {
                this.activo = true; // El lobby se activa cuando hay 2 jugadores
                this.turnoActual = jugadores.get(0); // El primer jugador tiene el turno
            }
        }
    }

    public boolean estaCompleto() {
        return jugadores.size() == 2;
    }
}
