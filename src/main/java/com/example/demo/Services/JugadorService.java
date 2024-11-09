package com.example.demo.Services;

import com.example.demo.Modelos.Jugador;
import com.example.demo.Modelos.Saldo;
import com.example.demo.Modelos.Usuario;
import com.example.demo.Repositorios.JugadorRepository;
import com.example.demo.Repositorios.SaldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JugadorService {

    private JugadorRepository jugadorRepository;

    private SaldoRepository saldoRepository;

    @Autowired
    public JugadorService(SaldoRepository saldoRepository, JugadorRepository jugadorRepository) {
        this.saldoRepository = saldoRepository;
        this.jugadorRepository = jugadorRepository;
    }

    public void crearNuevoJugador(Usuario usuario) {
        Saldo saldo = new Saldo(5.0);
        saldoRepository.save(saldo);
        Jugador jugador = new Jugador(usuario, 0, 0, saldo);
        jugadorRepository.save(jugador);
    }

    public double ObtenerMontoUsuario(Usuario usuario) {
        Optional<Jugador> jugadorOptional = jugadorRepository.findByUsuario(usuario);
        if (jugadorOptional.isPresent()) {
            Jugador jugador = jugadorOptional.get();
            return jugador.getSaldo().getMonto();
        }
        return 0;
    }

    public void actualizarPuntajeMax(Jugador jugador,int puntaje){
        jugador.setPuntajeMaximoSP(puntaje);
        jugadorRepository.save(jugador);
    }

    public Optional<Jugador> encontrarPorUsuario(Usuario usuario) {
        return jugadorRepository.findByUsuario(usuario);
    }
}