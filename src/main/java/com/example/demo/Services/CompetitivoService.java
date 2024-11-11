package com.example.demo.Services;

import com.example.demo.Modelos.Competitivo;
import com.example.demo.Repositorios.CompetitivoRepository;

public class CompetitivoService {
    private CompetitivoRepository competitivoRepository;

    public void guardarPartidaCompetitiva(Competitivo competitivo){
        competitivoRepository.save(competitivo);
    }
}
