package com.example.demo.Controllers;

import com.example.demo.Modelos.Pregunta;
import com.example.demo.Services.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatgpt")
public class ChatGptController {

    private final OpenAIService openAIService;

    @Autowired
    public ChatGptController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/pregunta")
    public Pregunta obtenerPregunta(@RequestParam String categoria) {
        return openAIService.generarPregunta(categoria);
    }
}
