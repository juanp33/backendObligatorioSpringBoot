package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Modelos.Saldo;
import com.example.demo.Repositorios.SaldoRepository;

@Service
public class SaldoService {

    @Autowired
    private SaldoRepository saldoRepository;

    public void guardarSaldo(Saldo saldo){
        saldoRepository.save(saldo);
    }
}
