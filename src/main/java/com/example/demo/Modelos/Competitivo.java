package com.example.demo.Modelos;

import jakarta.persistence.Entity;

@Entity
public class Competitivo extends Juego implements Ganador {
    private int puntajeJ1;
    private int puntajeJ2;
    private int tiempo;
}