package com.example.demo.Controllers;

import com.example.demo.APIRequest.DepositRequest;
import com.example.demo.Modelos.Jugador;
import com.example.demo.Modelos.Saldo;
import com.example.demo.Modelos.Usuario;
import com.example.demo.Repositorios.JugadorRepository;
import com.example.demo.Repositorios.SaldoRepository;
import com.example.demo.Repositorios.UsuarioRepository;
import com.example.demo.Services.JugadorService;
import com.example.demo.Services.SaldoService;
import com.example.demo.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cajero")
public class CajeroController {

    @Autowired
    private JugadorService jugadorService;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SaldoService saldoService;

    // Método para depositar saldo
    @PostMapping("/depositar")
    public ResponseEntity<String> depositarSaldo(@RequestBody DepositRequest request) {

        String username = request.getUsername();
        double monto = request.getMonto();
        Optional<Usuario> usuarioOpt = usuarioService.encontrarPorUsername(username);


            Usuario usuario = usuarioOpt.get();

            Optional<Jugador> jugadorOpt = jugadorService.encontrarPorUsuario(usuario);


                Jugador jugador = jugadorOpt.get();
                Saldo saldo = jugador.getSaldo();

                if (saldo != null) {
                    // Aumentar el saldo
                    saldo.cargarSaldo(monto);
                    saldoService.guardarSaldo(saldo);

                    return ResponseEntity.ok("Depósito realizado con éxito.");
                } else {
                    return ResponseEntity.unprocessableEntity().body("El jugador no tiene saldo inicial.");
                }
            }


    @PostMapping("/retirar")
    public ResponseEntity<String> retirarSaldo(@RequestBody DepositRequest request) {
        String username = request.getUsername();
        double monto = request.getMonto();
        Optional<Usuario> usuarioOpt = usuarioService.encontrarPorUsername(username);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Buscar el jugador asociado a ese usuario
            Optional<Jugador> jugadorOpt = jugadorService.encontrarPorUsuario(usuario);

            if (jugadorOpt.isPresent()) {
                Jugador jugador = jugadorOpt.get();
                Saldo saldo = jugador.getSaldo();

                if (saldo != null) {
                    if (monto > saldo.getMonto()) {
                        return ResponseEntity.badRequest().body("Saldo insuficiente.");
                    }
                    // Disminuir el saldo
                    saldo.modificarSaldo(saldo.getMonto() - monto);
                    saldoService.guardarSaldo(saldo);
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
