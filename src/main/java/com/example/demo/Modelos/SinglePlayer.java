package com.example.demo.Modelos;

import jakarta.persistence.*;

@Entity

public class SinglePlayer  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int puntaje;
    private int tiempo;
}