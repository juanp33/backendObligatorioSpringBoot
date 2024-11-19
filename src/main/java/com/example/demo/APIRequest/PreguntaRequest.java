package com.example.demo.APIRequest;

import com.example.demo.Modelos.Pregunta;

public class PreguntaRequest {
    public Pregunta pregunta;

    public String turnoActivo;
    public String categoria;

    public PreguntaRequest(Pregunta pregunta, String turnoActivo, String categoria) {
        this.pregunta = pregunta;
        this.categoria=categoria;
        this.turnoActivo = turnoActivo;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getTurnoActivo() {
        return turnoActivo;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setTurnoActivo(String turnoActivo) {
        this.turnoActivo = turnoActivo;
    }
}
