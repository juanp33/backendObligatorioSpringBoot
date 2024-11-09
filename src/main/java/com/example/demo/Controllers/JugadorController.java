package com.example.demo.Controllers;

import com.example.demo.Modelos.Jugador;
import com.example.demo.Modelos.Usuario;
import com.example.demo.Services.JugadorService;
import com.example.demo.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {
    @Autowired
    private JugadorService jugadorService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/actpuntajemax")
    public ResponseEntity<String> actualizarPuntajeMaxJugador(@RequestParam String username, @RequestParam int puntaje){

        Optional<Usuario> usuarioOpt = usuarioService.encontrarPorUsername(username);
        try{
            Usuario usuario = usuarioOpt.get();

            Optional<Jugador> jugadorOpt = jugadorService.encontrarPorUsuario(usuario);
            Jugador jugador = jugadorOpt.get();

            jugadorService.actualizarPuntajeMax(jugador, puntaje);


            return ResponseEntity.ok("Record actualizado");
        }catch (Exception ex){
            return ResponseEntity.unprocessableEntity().body("Error al actualizar el puntaje maximo");
        }

    }

}
