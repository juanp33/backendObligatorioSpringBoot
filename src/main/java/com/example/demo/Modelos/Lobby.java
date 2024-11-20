package com.example.demo.Modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Lobby {
    @Id

    private String id;
    private List<String> jugadores;
    private String turnoActual;
    private boolean activo;

    // Constructor
    public Lobby(String id) {
        this.id = id;
        this.jugadores = new ArrayList<>();
        this.turnoActual = null;
        this.activo = false; // Inicialmente el lobby no está activo
    }
    public Lobby(){

    }
    // Getters y Setters
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
        if (!jugadores.contains(jugador) && jugadores.size() < 2) { // Verificar si el jugador no está ya en la lista y hay espacio
            jugadores.add(jugador);
            actualizarEstadoLobby();
        }
    }


    public void removerJugador(String jugador) {
        if (jugadores.contains(jugador)) {
            jugadores.remove(jugador);
            actualizarEstadoLobby();
        }
    }

    // Método para actualizar el estado del lobby
    private void actualizarEstadoLobby() {
        if (jugadores.size() == 1) {
            this.activo = true; // El lobby se activa si hay al menos un jugador
            this.turnoActual = jugadores.get(0); // Asignar turno al único jugador
        } else if (jugadores.size() == 2) {
            this.activo = true; // El lobby se mantiene activo con dos jugadores
        } else {
            this.activo = false; // El lobby se desactiva si no hay jugadores
            this.turnoActual = null; // No hay turno si no hay jugadores
        }
    }

    // Método para verificar si el lobby está completo
    public boolean estaCompleto() {
        return jugadores.size() == 2;
    }
}