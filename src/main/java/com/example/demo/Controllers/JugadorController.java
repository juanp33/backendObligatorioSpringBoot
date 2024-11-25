package com.example.demo.Controllers;

import com.example.demo.APIResponse.JugadorResponse;
import com.example.demo.Services.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;


    @GetMapping("/detalles")
    public List<JugadorResponse> obtenerJugadoresConDetalles() {
        return jugadorService.obtenerTodosLosJugadoresConDetalles();
    }
}