package com.example.demo.Repositorios;

import com.example.demo.Modelos.Competitivo;
import com.example.demo.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompetitivoRepository extends JpaRepository<Competitivo,Long> {

   Optional<Competitivo> findBylobbyID(String lobbyID);
}
