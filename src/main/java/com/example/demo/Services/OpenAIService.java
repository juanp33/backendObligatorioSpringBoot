package com.example.demo.Services;

import com.example.demo.Modelos.Pregunta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public static Pregunta generarPregunta(String categoria) {
        String prompt = "Genera una pregunta de " + categoria + " con cuatro opciones de respuesta, indicando la respuesta correcta. Formato:\n" +
                "Pregunta: <texto>\n" +
                "a) <opción 1>\n" +
                "b) <opción 2>\n" +
                "c) <opción 3>\n" +
                "d) <opción 4>\n" +
                "Respuesta correcta: <letra>";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Crear el cuerpo de la solicitud como un Map para luego convertirlo a JSON
            Map<String, Object> requestBodyMap = new HashMap<>();
            requestBodyMap.put("model", "gpt-3.5-turbo"); // Puedes usar "gpt-4" si tienes acceso
            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", prompt);
            messages.add(message);
            requestBodyMap.put("messages", messages);
            requestBodyMap.put("temperature", 0.7);
            requestBodyMap.put("max_tokens", 100);

            // Convertir el Map a JSON
            String requestBody = objectMapper.writeValueAsString(requestBodyMap);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Respuesta de la API: " + response.body());

            if (response.statusCode() != 200) {
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
                return null;
            }

            // Parsear la respuesta JSON usando Jackson
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            String respuestaGenerada = jsonResponse.get("choices").get(0).get("message").get("content").asText().trim();

            // Extraer partes de la respuesta
            String[] partes = respuestaGenerada.split("\n");
            String enunciado = partes[0].replace("Pregunta: ", "");
            List<String> respuestas = new ArrayList<>();
            respuestas.add(partes[1].substring(3)); // Opción a)
            respuestas.add(partes[2].substring(3)); // Opción b)
            respuestas.add(partes[3].substring(3)); // Opción c)
            respuestas.add(partes[4].substring(3)); // Opción d)
            String respuestaCorrecta = partes[5].replace("Respuesta correcta: ", "");

            // Crear y devolver el objeto Pregunta
            Pregunta pregunta = new Pregunta();
            pregunta.setEnunciado(enunciado);
            pregunta.setRespuestas(respuestas);
            pregunta.setRespuestaCorrecta(respuestaCorrecta);
            pregunta.setCategoria(categoria);

            return pregunta;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}