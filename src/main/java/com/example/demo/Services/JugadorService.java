package com.example.demo.Services;

import com.example.demo.APIResponse.JugadorResponse;
import com.example.demo.Modelos.Jugador;
import com.example.demo.Modelos.Saldo;
import com.example.demo.Modelos.Usuario;
import com.example.demo.Repositorios.JugadorRepository;
import com.example.demo.Repositorios.SaldoRepository;
import com.example.demo.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {
    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private SaldoRepository saldoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    public JugadorService(SaldoRepository saldoRepository, JugadorRepository jugadorRepository) {
        this.saldoRepository = saldoRepository;
        this.jugadorRepository = jugadorRepository;
    }
    public List<JugadorResponse> obtenerTodosLosJugadoresConDetalles(){
      return  jugadorRepository.findAllJugadoresWithDetails();

    }
    public void crearNuevoJugador(Usuario usuario) {
        Saldo saldo = new Saldo(5.0);
        saldoRepository.save(saldo);
        Jugador jugador = new Jugador(usuario, 0, 0,0, saldo);
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

    public Optional<Jugador> encontrarPorUsuario(Usuario usuario) {
        return jugadorRepository.findByUsuario(usuario);
    }
    public Optional<Jugador> encontrarPorNombre (String nombre){
        Optional<Usuario> usuario = usuarioRepository.findByUsername(nombre);
        Usuario usuariofinal = usuario.get();
        return encontrarPorUsuario(usuariofinal);
    }

    public void actualizarPartidaGanada(Jugador jugador1,Jugador jugador2, Jugador Ganador){
        if(Ganador==jugador1) {
            jugador1.setPartidasGanadas(jugador1.getPartidasGanadas() + 1);
            jugadorRepository.save(jugador1);
            jugador2.setPartidasPerdidas(jugador2.getPartidasPerdidas() + 1);
            jugadorRepository.save(jugador2);
        }else{
            jugador2.setPartidasGanadas(jugador2.getPartidasGanadas() + 1);
            jugadorRepository.save(jugador2);
            jugador1.setPartidasPerdidas(jugador1.getPartidasPerdidas() + 1);
            jugadorRepository.save(jugador1);
        }
    }

    public void actualizarPuntajeMaximo(Jugador jugador,int puntos){
                 jugador.setPuntajeMaximoSP(puntos);
                 jugadorRepository.save(jugador);
    }
}