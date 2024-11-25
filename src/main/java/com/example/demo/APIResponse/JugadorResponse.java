package com.example.demo.APIResponse;

public class JugadorResponse {
    private String username;
    private Integer puntajeMaximoSP;
    private Integer partidasGanadas;
    private Integer partidasPerdidas;

    // Constructor
    public JugadorResponse(String nombreUsuario, Integer puntajeMaximoSP, Integer partidasGanadas, Integer partidasPerdidas) {
        this.username = nombreUsuario;
        this.puntajeMaximoSP = puntajeMaximoSP;
        this.partidasGanadas = partidasGanadas;
        this.partidasPerdidas = partidasPerdidas;
    }

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String nombreUsuario) {
        this.username = nombreUsuario;
    }

    public Integer getPuntajeMaximoSP() {
        return puntajeMaximoSP;
    }

    public void setPuntajeMaximoSP(Integer puntajeMaximoSP) {
        this.puntajeMaximoSP = puntajeMaximoSP;
    }

    public Integer getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(Integer partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public Integer getPartidasPerdidas() {
        return partidasPerdidas;
    }

    public void setPartidasPerdidas(Integer partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }
}