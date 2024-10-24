package com.example.demo.Modelos;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Bots")
public class JugarConBot extends Juego implements Ganador {

;
    private String nivelDificultad; // Valores posibles: "facil", "medio", "dificil"
    private int puntajeJugador;
    private int puntajeBot;
    private int tiempo;
}