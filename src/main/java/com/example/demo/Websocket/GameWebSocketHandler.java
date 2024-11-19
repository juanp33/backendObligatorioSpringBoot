package com.example.demo.Websocket;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import java.util.concurrent.ConcurrentHashMap;

public class GameWebSocketHandler extends TextWebSocketHandler {
    private final ConcurrentHashMap<String, WebSocketSession> players = new ConcurrentHashMap<>();
    private String currentPlayerTurn = null;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        players.put(session.getId(), session);
        if (players.size() == 2 && currentPlayerTurn == null) {
            currentPlayerTurn = session.getId();
            sendMessageToPlayer(currentPlayerTurn, "Es tu turno!");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (!session.getId().equals(currentPlayerTurn)) return; // Ignorar si no es el turno del jugador


        String payload = message.getPayload();


        currentPlayerTurn = players.keySet().stream()
                .filter(id -> !id.equals(session.getId()))
                .findFirst()
                .orElse(currentPlayerTurn);

        sendMessageToPlayer(currentPlayerTurn, "Es tu turno!");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        players.remove(session.getId());
        if (players.isEmpty()) {
            currentPlayerTurn = null;
        }
    }

    private void sendMessageToPlayer(String playerId, String message) throws Exception {
        WebSocketSession playerSession = players.get(playerId);
        if (playerSession != null && playerSession.isOpen()) {
            playerSession.sendMessage(new TextMessage(message));
        }
    }
}