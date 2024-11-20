package com.example.demo.Repositorios;

import com.example.demo.Modelos.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LobbyRepository extends JpaRepository<Lobby, String> {
}