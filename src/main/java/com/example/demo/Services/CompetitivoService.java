package com.example.demo.Services;

import com.example.demo.Modelos.Competitivo;
import com.example.demo.Repositorios.CompetitivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitivoService {
    @Autowired
    private CompetitivoRepository competitivoRepository;

    public void guardarPartidaCompetitiva(Competitivo competitivo){
        try{ competitivoRepository.save(competitivo);}
        catch(Exception ex){

        }

    }
}
