package Modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Apuestas extends Juego implements Ganador {
    @OneToOne
    private Pozo pozo;
    private double apuestaActual;
}