package com.example.demo.Modelos;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idJuego;

    @ManyToMany
    private List<Jugador> jugadores;



    @OneToOne
    private Pregunta pregunta;

    private Integer turno;
}