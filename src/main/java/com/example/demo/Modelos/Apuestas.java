package com.example.demo.Modelos;

import jakarta.persistence.*;

@Entity
public class Apuestas  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Pozo pozo;
    private double apuestaActual;
}