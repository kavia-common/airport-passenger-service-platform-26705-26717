package com.example.intelligentchatbotservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for the Intelligent Chatbot Service.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI intelligentChatbotOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Intelligent Chatbot Service API")
                        .description("REST + WebSocket APIs for chatbot conversations and messages.")
                        .version("0.1.0"));
    }
}
