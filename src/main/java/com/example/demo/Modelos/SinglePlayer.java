package com.example.demo.Modelos;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("SinglePlayer")
public class SinglePlayer extends Juego {


    private int puntaje;
    private int tiempo;
}