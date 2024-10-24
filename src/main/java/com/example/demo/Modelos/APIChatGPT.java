package com.example.demo.Modelos;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class APIChatGPT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Pregunta> preguntas;
}