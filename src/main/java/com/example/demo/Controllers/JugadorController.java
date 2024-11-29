package com.example.demo.Controllers;

import com.example.demo.APIRequest.ActualizarPuntajeRequest;
import com.example.demo.APIRequest.GameFinishRequest;
import com.example.demo.APIResponse.JugadorResponse;
import com.example.demo.Modelos.Jugador;
import com.example.demo.Modelos.Usuario;
import com.example.demo.Services.JugadorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;


    @GetMapping("/detalles")
    public List<JugadorResponse> obtenerJugadoresConDetalles() {
        return jugadorService.obtenerTodosLosJugadoresConDetalles();
    }

    @PostMapping("/actualizarPuntaje")
    public ResponseEntity<String> actualizarPuntaje(@RequestParam String nombre, @RequestParam int puntos) {
        System.out.println("Nombre: " + nombre);  // Log para verificar el nombre
        System.out.println("Puntos: " + puntos);  // Log para verificar los puntos

        Optional<Jugador> jugadorOptional = jugadorService.encontrarPorNombre(nombre);

        if (jugadorOptional.isPresent()) {
            Jugador jugador = jugadorOptional.get();
            jugadorService.actualizarPuntajeMaximo(jugador, puntos);
            return ResponseEntity.ok("Puntaje actualizado.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jugador no encontrado.");
        }
    }
}