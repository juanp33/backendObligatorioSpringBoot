package com.example.demo.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "Jugadores")
public class Jugador   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJugador;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    private Integer puntajeMaximoSP;
    private Integer partidasGanadas;
    private Integer partidasPerdidas;

    @OneToOne
    @JoinColumn(name = "saldo_id", referencedColumnName = "id")
    private Saldo saldo;

    // Constructor vacío necesario para JPA
    public Jugador() {}

    // Constructor con parámetros
    public Jugador(Usuario usuario, Integer puntajeMaximoSP, Integer partidasGanadas, Integer partidasPerdidas, Saldo saldo) {
        this.usuario = usuario;
        this.puntajeMaximoSP = puntajeMaximoSP;
        this.partidasGanadas = partidasGanadas;
        this.partidasPerdidas = partidasPerdidas;
        this.saldo = saldo;
    }

    // Getters y Setters
    public Long getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Long idJugador) {
        this.idJugador = idJugador;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getPuntajeMaximoSP() {
        return puntajeMaximoSP;
    }

    public void setPuntajeMaximoSP(Integer puntajeMaximoSP) {
        this.puntajeMaximoSP = puntajeMaximoSP;
    }

    public Integer getPartidasPerdidas() {
        return partidasPerdidas;
    }

    public Integer getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(Integer partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public void setPartidasPerdidas(Integer partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }

    public Saldo getSaldo() {
        return saldo;
    }

    public void setSaldo(Saldo saldo) {
        this.saldo = saldo;
    }
}
