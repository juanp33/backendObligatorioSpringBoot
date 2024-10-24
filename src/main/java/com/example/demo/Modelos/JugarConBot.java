package com.example.demo.Modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class JugarConBot extends Juego implements Ganador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nivelDificultad; // Valores posibles: "facil", "medio", "dificil"
    private int puntajeJugador;
    private int puntajeBot;
    private int tiempo;
}