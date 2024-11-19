package com.example.demo.Services;

import com.example.demo.Modelos.Competitivo;
import com.example.demo.Modelos.Jugador;
import com.example.demo.Modelos.Saldo;
import com.example.demo.Repositorios.CompetitivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CompetitivoService {
    @Autowired
    private CompetitivoRepository competitivoRepository;

    public void guardarPartidaCompetitiva(Competitivo competitivo){
        try{ competitivoRepository.save(competitivo);}
        catch(Exception ex){

        }

    }

    public void actualizarPuntajes(int puntosPlayer1,int puntosPlayer2,String lobbyID){
        Optional<Competitivo> CompetitivoOptional= competitivoRepository.findBylobbyID(lobbyID);
        if (CompetitivoOptional.isPresent()) {
            Competitivo competitivo = CompetitivoOptional.get();
            competitivo.setPuntajeJ1(puntosPlayer1);
            competitivo.setPuntajeJ2(puntosPlayer2);
            competitivoRepository.save(competitivo);
        }

    }


}
