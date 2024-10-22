package Modelos;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPregunta;
    private String enunciado;
    @ElementCollection
    private List<String> respuestas;
    private String respuestaCorrecta;
    private String categoria;
}