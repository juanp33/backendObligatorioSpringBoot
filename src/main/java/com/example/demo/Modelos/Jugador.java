package com.example.demo.Modelos;

import jakarta.persistence.*;


@Entity
@Table(name = "Jugadores")
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idJugador;
    private String nombre;
    private String email;
    private String contrasena;
    private Integer puntajeMaximoSP;
    private Integer puntajeCompetitivo;
}