package com.example.intelligentchatbotservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Conversation entity mapped to chatbot_conversations table (created by Flyway).
 */
@Entity
@Table(name = "chatbot_conversations")
public class Conversation {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "passenger_id")
    private UUID passengerId;

    @Column(name = "language_code", nullable = false, length = 10)
    private String languageCode = "en";

    @Column(name = "channel", nullable = false, length = 30)
    private String channel = "text";

    @Column(name = "status", nullable = false, length = 30)
    private String status = "OPEN";

    /**
     * Stored as jsonb in Postgres. We keep it as String to avoid extra dependencies.
     * Can be migrated to JsonNode via a converter later.
     */
    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void prePersist() {
        // For rows created by JPA, set created/updated if DB defaults are not applied.
        // DB already defaults to now(), but this keeps object graph consistent before flush.
        OffsetDateTime now = OffsetDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = now;
        }
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(UUID passengerId) {
        this.passengerId = passengerId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
