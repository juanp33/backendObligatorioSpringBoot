package com.example.demo.Services;

import com.example.demo.Modelos.Pregunta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class OpenAIService {
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public static Pregunta generarPregunta(String categoria) {

        String prompt = "Genera 5 preguntas de " + categoria + " con cuatro opciones de respuesta para cada una, indicando la respuesta correcta. No repitas ninguna pregunta y asegúrate de que cada pregunta sea única, puede ser de muchos aspectos de la categoria, se creativo, en la rama arte por ejemplo evita preguntas con Quien pinto?, en geografia puedes preguntar capitales, paises, lugares, ciudades, categorias geograficas, evita preguntas torre eiffel y montañas. Formato:\n" +
                "Pregunta: <texto>\n" +
                "a) <opción 1>\n" +
                "b) <opción 2>\n" +
                "c) <opción 3>\n" +
                "d) <opción 4>\n" +
                "Respuesta correcta: <letra>\n" +
                "Genera todas las preguntas en el formato mencionado y separa cada pregunta por una línea en blanco.";

        try {
            // Código para hacer la solicitud HTTP como antes
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> requestBodyMap = new HashMap<>();
            requestBodyMap.put("model", "gpt-3.5-turbo");
            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", prompt);
            messages.add(message);
            requestBodyMap.put("messages", messages);
            requestBodyMap.put("temperature", 1);
            requestBodyMap.put("max_tokens", 1600); // Aumenta el número de tokens según el contenido de múltiples preguntas

            String requestBody = objectMapper.writeValueAsString(requestBodyMap);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
                return null;
            }

            // Parsear la respuesta
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            String respuestaGenerada = jsonResponse.get("choices").get(0).get("message").get("content").asText().trim();

            // Dividir las preguntas generadas en una lista
            String[] preguntas = respuestaGenerada.split("\n\n"); // Cada pregunta está separada por una línea en blanco

            // Seleccionar una pregunta aleatoria
            Random random = new Random();
            String preguntaSeleccionada = preguntas[random.nextInt(preguntas.length)];

            // Procesar la pregunta seleccionada en partes (enunciado, opciones y respuesta correcta)
            String[] partes = preguntaSeleccionada.split("\n");
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