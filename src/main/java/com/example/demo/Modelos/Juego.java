package com.example.demo.Modelos;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Puedes usar otros tipos de herencia como JOINED o TABLE_PER_CLASS
@DiscriminatorColumn(name = "tipo_juego", discriminatorType = DiscriminatorType.STRING)
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