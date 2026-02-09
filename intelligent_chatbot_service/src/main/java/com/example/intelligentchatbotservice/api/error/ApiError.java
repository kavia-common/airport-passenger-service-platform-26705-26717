package com.example.intelligentchatbotservice.api.error;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Standard API error payload.
 */
public class ApiError {

    private final OffsetDateTime timestamp = OffsetDateTime.now();
    private final String code;
    private final String message;
    private final Map<String, Object> details;

    public ApiError(String code, String message, Map<String, Object> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}
