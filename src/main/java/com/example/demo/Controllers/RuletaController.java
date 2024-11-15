package com.example.demo.Controllers;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Service

public class RuletaController {

    private static final String[] Categorias = {
            "Arte",
            "Entretenimiento",
            "Ciencia",
            "Geografía, paises, capitales, etc",
            "Historia mundial",
            "Deportes"
    };

    @GetMapping("/random-categoria")
    public String getRandomCategoria() {
        Random random = new Random();
        int randomIndex = random.nextInt(Categorias.length);
        return Categorias[randomIndex];
    }
}