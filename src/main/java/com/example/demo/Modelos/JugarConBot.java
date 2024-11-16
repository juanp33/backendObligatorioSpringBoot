package com.example.demo.Modelos;

import jakarta.persistence.*;

@Entity

public class JugarConBot  {

;  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nivelDificultad; // Valores posibles: "facil", "medio", "dificil"
    private int puntajeJugador;
    private int puntajeBot;
    private int tiempo;
}