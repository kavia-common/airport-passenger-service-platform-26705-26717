package com.example.intelligentchatbotservice.api;

import com.example.intelligentchatbotservice.api.dto.ConversationResponse;
import com.example.intelligentchatbotservice.api.dto.MessageResponse;
import com.example.intelligentchatbotservice.api.dto.PostMessageRequest;
import com.example.intelligentchatbotservice.api.dto.StartConversationRequest;
import com.example.intelligentchatbotservice.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for conversations and messages.
 */
@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chat", description = "Conversation and message APIs")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // PUBLIC_INTERFACE
    @PostMapping("/conversations")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Start a conversation",
            description = "Creates a new chatbot conversation for a passenger and returns the created conversation."
    )
    public ConversationResponse startConversation(@Valid @RequestBody StartConversationRequest request) {
        return chatService.startConversation(request);
    }

    // PUBLIC_INTERFACE
    @GetMapping("/conversations/{conversationId}")
    @Operation(summary = "Get conversation", description = "Fetch a single conversation by id.")
    public ConversationResponse getConversation(
            @PathVariable UUID conversationId
    ) {
        return chatService.getConversation(conversationId);
    }

    // PUBLIC_INTERFACE
    @GetMapping("/conversations")
    @Operation(
            summary = "List conversations",
            description = "Lists conversations. Optionally filter by passengerId."
    )
    public Page<ConversationResponse> listConversations(
            @RequestParam(required = false) UUID passengerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return chatService.listConversations(passengerId, page, size);
    }

    // PUBLIC_INTERFACE
    @PostMapping("/conversations/{conversationId}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Post a message",
            description = "Appends a message (USER/BOT/SYSTEM) to a conversation. In future, BOT replies will be generated asynchronously."
    )
    public MessageResponse postMessage(
            @PathVariable UUID conversationId,
            @Valid @RequestBody PostMessageRequest request
    ) {
        return chatService.postMessage(conversationId, request);
    }

    // PUBLIC_INTERFACE
    @GetMapping("/conversations/{conversationId}/messages")
    @Operation(
            summary = "Read messages",
            description = "Returns messages for a conversation. Supports optional 'after' filter and 'limit' cap."
    )
    public List<MessageResponse> getMessages(
            @PathVariable UUID conversationId,
            @Parameter(description = "Return messages created strictly after this timestamp (ISO-8601).")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime after,
            @RequestParam(defaultValue = "200") int limit
    ) {
        return chatService.getMessages(conversationId, after, limit);
    }
}
