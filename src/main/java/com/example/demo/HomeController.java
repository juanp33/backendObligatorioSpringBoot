package com.example.demo;

import com.example.demo.Modelos.Jugador;

import com.example.demo.Repositorios.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private JugadorRepository userRepository;

    @GetMapping("/")
    public String checkDatabaseConnection() {
        try {
            List<Jugador> users = userRepository.findAll();
            return "Conexión a la base de datos MySQL exitosa. Usuarios en la base de datos: " + users.size();
        } catch (Exception e) {
            return "Error al conectar con la base de datos: " + e.getMessage();
        }
    }
}