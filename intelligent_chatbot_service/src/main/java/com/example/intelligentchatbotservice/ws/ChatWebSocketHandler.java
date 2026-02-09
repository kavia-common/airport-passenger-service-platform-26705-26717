package com.example.intelligentchatbotservice.ws;

import java.io.IOException;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Stub WebSocket handler for live chat.
 *
 * Protocol (stub):
 *  - Client connects to /ws/chat
 *  - Server sends a greeting and echoes back a stub acknowledgement for each message received.
 *
 * A future implementation should:
 *  - authenticate sessions
 *  - associate session with conversationId
 *  - persist messages and stream BOT responses
 */
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage("{\"type\":\"WELCOME\",\"ts\":\"" + OffsetDateTime.now() + "\"}"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Stub: acknowledge receipt
        session.sendMessage(new TextMessage("{\"type\":\"ACK\",\"receivedBytes\":" + message.getPayloadLength() + "}"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // no-op
    }
}
