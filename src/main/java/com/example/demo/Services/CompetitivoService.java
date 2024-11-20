package com.example.demo.Services;

import com.example.demo.Modelos.Competitivo;
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
    public Competitivo encontrarCompetitivoPorLobbyId(String lobbyID){
        Optional<Competitivo> CompetitivoOptional = competitivoRepository.findBylobbyID(lobbyID);
            Competitivo competitivo = CompetitivoOptional.get();
            return competitivo;

    }

    public void finalizarPartidaCompetitiva(int puntosPlayer1,int puntosPlayer2,String lobbyID, String estado){
        Optional<Competitivo> CompetitivoOptional= competitivoRepository.findBylobbyID(lobbyID);
        if (CompetitivoOptional.isPresent()) {
            Competitivo competitivo = CompetitivoOptional.get();
            competitivo.setPuntajeJ1(puntosPlayer1);
            competitivo.setPuntajeJ2(puntosPlayer2);
            competitivo.setEstado(estado);
            competitivoRepository.save(competitivo);
        }

    }


}
