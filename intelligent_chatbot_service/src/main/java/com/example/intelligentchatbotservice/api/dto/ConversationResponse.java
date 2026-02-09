package com.example.intelligentchatbotservice.api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Conversation response.
 */
public class ConversationResponse {

    private UUID id;
    private UUID passengerId;
    private String languageCode;
    private String channel;
    private String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public ConversationResponse() {}

    public ConversationResponse(
            UUID id,
            UUID passengerId,
            String languageCode,
            String channel,
            String status,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt
    ) {
        this.id = id;
        this.passengerId = passengerId;
        this.languageCode = languageCode;
        this.channel = channel;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPassengerId() {
        return passengerId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getChannel() {
        return channel;
    }

    public String getStatus() {
        return status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
