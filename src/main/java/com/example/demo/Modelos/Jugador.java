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
    private Integer puntajeCompetitivo;

    @OneToOne
    @JoinColumn(name = "saldo_id", referencedColumnName = "id")
    private Saldo saldo;

    // Constructor vacío necesario para JPA
    public Jugador() {}

    // Constructor con parámetros
    public Jugador(Usuario usuario, Integer puntajeMaximoSP, Integer puntajeCompetitivo, Saldo saldo) {
        this.usuario = usuario;
        this.puntajeMaximoSP = puntajeMaximoSP;
        this.puntajeCompetitivo = puntajeCompetitivo;
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

    public Integer getPuntajeCompetitivo() {
        return puntajeCompetitivo;
    }

    public void setPuntajeCompetitivo(Integer puntajeCompetitivo) {
        this.puntajeCompetitivo = puntajeCompetitivo;
    }

    public Saldo getSaldo() {
        return saldo;
    }

    public void setSaldo(Saldo saldo) {
        this.saldo = saldo;
    }
}
