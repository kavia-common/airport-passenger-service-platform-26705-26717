package com.example.intelligentchatbotservice.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

/**
 * Request to start a conversation.
 */
public class StartConversationRequest {

    private UUID passengerId;

    @NotBlank(message = "languageCode is required")
    @Pattern(regexp = "^[a-zA-Z]{2}(-[a-zA-Z]{2})?$", message = "languageCode must look like 'en' or 'en-IN'")
    private String languageCode;

    private String channel = "text";

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
}
