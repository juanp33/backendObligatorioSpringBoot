package com.example.demo.Modelos;

import jakarta.persistence.*;

@Entity

public class Competitivo  implements Ganador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCompetitivo;
    @ManyToOne
    @JoinColumn(name = "jugador1_id", referencedColumnName = "idJugador")
    private Jugador jugador1;

    @ManyToOne
    @JoinColumn(name = "jugador2_id", referencedColumnName = "idJugador")
    private Jugador jugador2;
    private int puntajeJ1;
    private int puntajeJ2;
    private String lobbyID;
    private String estado;
    public Competitivo(Jugador jugador1, Jugador jugador2, String lobbyID,String estado) {
        this.jugador2 = jugador2;
        this.jugador1 = jugador1;
        this.lobbyID = lobbyID;
        this.puntajeJ1 = 0;
        this.puntajeJ2=0;
        this.estado=estado;
    }

    public Competitivo() {
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public int getPuntajeJ2() {
        return puntajeJ2;
    }

    public int getPuntajeJ1() {
        return puntajeJ1;
    }

    public String getLobbyID() {
        return lobbyID;
    }

    public String getEstado() {
        return estado;
    }

    public int getIdCompetitivo() {
        return idCompetitivo;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }



    public void setPuntajeJ2(int puntajeJ2) {
        this.puntajeJ2 = puntajeJ2;
    }

    public void setPuntajeJ1(int puntajeJ1) {
        this.puntajeJ1 = puntajeJ1;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }

    public void setIdCompetitivo(int idCompetitivo) {
        this.idCompetitivo = idCompetitivo;
    }

    public void setLobbyID(String lobbyID) {
        this.lobbyID = lobbyID;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}