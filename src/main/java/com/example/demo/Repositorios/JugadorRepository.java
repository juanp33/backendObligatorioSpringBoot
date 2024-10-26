package com.example.demo.Repositorios;

import com.example.demo.Modelos.Jugador;
import com.example.demo.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    // Método para encontrar el jugador basado en el Usuario
    Optional<Jugador> findByUsuario(Usuario usuario);
}
