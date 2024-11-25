package com.example.demo.Repositorios;

import com.example.demo.Modelos.Jugador;
import com.example.demo.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    // Método para encontrar el jugador basado en el Usuario
    Optional<Jugador> findByUsuario(Usuario usuario);
    @Query("SELECT new com.example.demo.APIResponse.JugadorResponse(j.usuario.username, j.puntajeMaximoSP, j.partidasGanadas, j.partidasPerdidas) FROM Jugador j")
    List<com.example.demo.APIResponse.JugadorResponse> findAllJugadoresWithDetails();
}
