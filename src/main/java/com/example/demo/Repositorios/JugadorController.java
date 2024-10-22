package com.example.demo.Repositorios;

import com.example.demo.Modelos.Jugador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorController extends JpaRepository<Jugador, Long> {
    // Métodos adicionales de consulta si los necesitas
}
