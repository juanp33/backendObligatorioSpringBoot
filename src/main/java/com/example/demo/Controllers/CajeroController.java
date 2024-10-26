package com.example.demo.Controllers;

import com.example.demo.APIRequest.DepositRequest;
import com.example.demo.Modelos.Jugador;
import com.example.demo.Modelos.Saldo;
import com.example.demo.Modelos.Usuario;
import com.example.demo.Repositorios.JugadorRepository;
import com.example.demo.Repositorios.SaldoRepository;
import com.example.demo.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cajero")
public class CajeroController {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SaldoRepository saldoRepository;

    // Método para depositar saldo
    @PostMapping("/depositar")
    public ResponseEntity<String> depositarSaldo(@RequestBody DepositRequest request) {

        String username = request.getUsername();
        double monto = request.getMonto();
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);


            Usuario usuario = usuarioOpt.get();

            Optional<Jugador> jugadorOpt = jugadorRepository.findByUsuario(usuario);


                Jugador jugador = jugadorOpt.get();
                Saldo saldo = jugador.getSaldo();

                if (saldo != null) {
                    // Aumentar el saldo
                    saldo.cargarSaldo(monto);
                    saldoRepository.save(saldo);

                    return ResponseEntity.ok("Depósito realizado con éxito.");
                } else {
                    return ResponseEntity.unprocessableEntity().body("El jugador no tiene saldo inicial.");
                }
            }


    @PostMapping("/retirar")
    public ResponseEntity<String> retirarSaldo(@RequestBody DepositRequest request) {
        String username = request.getUsername();
        double monto = request.getMonto();
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
                    saldoRepository.save(saldo);
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
