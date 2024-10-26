package com.example.demo.Modelos;

import jakarta.persistence.*;

@Entity
public class Saldo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monto;

    // Constructor vacío (necesario para JPA)
    public Saldo() {}

    // Constructor para inicializar con un monto
    public Saldo(Double monto) {
        this.monto = monto;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    // Método para cargar saldo
    public void cargarSaldo(double monto) {
        this.monto += monto;
    }

    // Método para modificar saldo (por ejemplo, para retirar dinero)
    public void modificarSaldo(double monto) {
        this.monto = monto;
    }
}
