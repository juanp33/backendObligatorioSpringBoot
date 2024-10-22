package com.example.demo.Modelos;

import jakarta.persistence.*;

@Entity
public class Saldo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double monto;
    private Integer idJugador;

    public void cargarSaldo(double monto) {
        this.monto += monto;
    }

    public void modificarSaldo(double monto) {
        this.monto = monto;
    }
}
