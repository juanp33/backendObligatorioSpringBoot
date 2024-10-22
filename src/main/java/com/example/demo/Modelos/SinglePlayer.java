package com.example.demo.Modelos;

import jakarta.persistence.Entity;

@Entity
public class SinglePlayer extends Juego {
    private int puntaje;
    private int tiempo;
}