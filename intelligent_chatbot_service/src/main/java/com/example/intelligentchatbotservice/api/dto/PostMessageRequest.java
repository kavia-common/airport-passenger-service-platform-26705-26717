package com.example.intelligentchatbotservice.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Request to post a message to a conversation.
 */
public class PostMessageRequest {

    @NotBlank(message = "role is required")
    @Pattern(regexp = "^(USER|BOT|SYSTEM)$", message = "role must be one of USER, BOT, SYSTEM")
    private String role;

    @NotBlank(message = "content is required")
    private String content;

    private String messageType = "text";

    /**
     * Optional json string that will be stored in payload jsonb.
     */
    private String payload;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
