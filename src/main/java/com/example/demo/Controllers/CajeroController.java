package com.example.demo.Controllers;

import com.example.demo.Modelos.Jugador;
import com.example.demo.Modelos.Saldo;
import com.example.demo.Modelos.Usuario;
import com.example.demo.Repositorios.JugadorRepository;
import com.example.demo.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cajero")
public class CajeroController {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para depositar saldo
    @PostMapping("/depositar")
    public ResponseEntity<String> depositarSaldo(@RequestParam String username, @RequestParam double monto) {
        // Buscar el objeto Usuario por su nombre de usuario
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Buscar el jugador asociado a ese usuario
            Optional<Jugador> jugadorOpt = jugadorRepository.findByUsuario(usuario);

            if (jugadorOpt.isPresent()) {
                Jugador jugador = jugadorOpt.get();
                Saldo saldo = jugador.getSaldo();

                if (saldo != null) {
                    // Aumentar el saldo
                    saldo.cargarSaldo(monto);
                    jugadorRepository.save(jugador);  // Guardar cambios
                    return ResponseEntity.ok("Depósito realizado con éxito.");
                } else {
                    return ResponseEntity.badRequest().body("El jugador no tiene saldo inicial.");
                }
            } else {
                return ResponseEntity.badRequest().body("Jugador no encontrado.");
            }
        } else {
            return ResponseEntity.badRequest().body("Usuario no encontrado.");
        }
    }

    // Método para retirar saldo
    @PostMapping("/retirar")
    public ResponseEntity<String> retirarSaldo(@RequestParam String username, @RequestParam double monto) {
        // Buscar el objeto Usuario por su nombre de usuario
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Buscar el jugador asociado a ese usuario
            Optional<Jugador> jugadorOpt = jugadorRepository.findByUsuario(usuario);

            if (jugadorOpt.isPresent()) {
                Jugador jugador = jugadorOpt.get();
                Saldo saldo = jugador.getSaldo();

                if (saldo != null) {
                    if (monto > saldo.getMonto()) {
                        return ResponseEntity.badRequest().body("Saldo insuficiente.");
                    }
                    // Disminuir el saldo
                    saldo.modificarSaldo(saldo.getMonto() - monto);
                    jugadorRepository.save(jugador);  // Guardar cambios
                    return ResponseEntity.ok("Retiro realizado con éxito.");
                } else {
                    return ResponseEntity.badRequest().body("El jugador no tiene saldo inicial.");
                }
            } else {
                return ResponseEntity.badRequest().body("Jugador no encontrado.");
            }
        } else {
            return ResponseEntity.badRequest().body("Usuario no encontrado.");
        }
    }
}
