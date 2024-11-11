package com.example.demo.Modelos;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Competitivo")
public class Competitivo extends Juego implements Ganador {
    public Competitivo(Jugador jugador1, Jugador jugador2) {
        this.jugador2 = jugador2;
        this.jugador1 = jugador1;
    }

    private Jugador jugador1;
    private Jugador jugador2;
    private int puntajeJ1;
    private int puntajeJ2;
    private int tiempo;

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public int getPuntajeJ2() {
        return puntajeJ2;
    }

    public int getPuntajeJ1() {
        return puntajeJ1;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void setPuntajeJ2(int puntajeJ2) {
        this.puntajeJ2 = puntajeJ2;
    }

    public void setPuntajeJ1(int puntajeJ1) {
        this.puntajeJ1 = puntajeJ1;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }
}