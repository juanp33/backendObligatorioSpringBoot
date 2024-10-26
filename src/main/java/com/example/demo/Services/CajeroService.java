package com.example.demo.Services;

import com.example.demo.Modelos.Saldo;
import com.example.demo.Repositorios.SaldoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CajeroService {
    @Autowired
    private SaldoRepository SaldoRepository;

    public void GuardarSaldo(Saldo saldo) {
        SaldoRepository.save(saldo);
    }
}
