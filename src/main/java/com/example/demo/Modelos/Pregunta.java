package com.example.demo.Modelos;

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

    public Pregunta() {

    }

    public Integer getIdPregunta() {
        return idPregunta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getRespuestas() {
        return respuestas;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public void setRespuestas(List<String> respuestas) {
        this.respuestas = respuestas;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
