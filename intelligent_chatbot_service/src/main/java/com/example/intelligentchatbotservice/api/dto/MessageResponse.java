package com.example.intelligentchatbotservice.api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Message response.
 */
public class MessageResponse {

    private UUID id;
    private UUID conversationId;
    private String role;
    private String messageType;
    private String content;
    private String payload;
    private OffsetDateTime createdAt;

    public MessageResponse() {}

    public MessageResponse(
            UUID id,
            UUID conversationId,
            String role,
            String messageType,
            String content,
            String payload,
            OffsetDateTime createdAt
    ) {
        this.id = id;
        this.conversationId = conversationId;
        this.role = role;
        this.messageType = messageType;
        this.content = content;
        this.payload = payload;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public String getRole() {
        return role;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getContent() {
        return content;
    }

    public String getPayload() {
        return payload;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
