package com.example.intelligentchatbotservice.service;

import com.example.intelligentchatbotservice.api.dto.ConversationResponse;
import com.example.intelligentchatbotservice.api.dto.MessageResponse;
import com.example.intelligentchatbotservice.api.dto.PostMessageRequest;
import com.example.intelligentchatbotservice.api.dto.StartConversationRequest;
import com.example.intelligentchatbotservice.domain.Conversation;
import com.example.intelligentchatbotservice.domain.Message;
import com.example.intelligentchatbotservice.repo.ConversationRepository;
import com.example.intelligentchatbotservice.repo.MessageRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Chat domain service for conversations and messages.
 */
@Service
public class ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public ChatService(ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public ConversationResponse startConversation(StartConversationRequest request) {
        Conversation c = new Conversation();
        c.setPassengerId(request.getPassengerId());
        c.setLanguageCode(request.getLanguageCode());
        if (request.getChannel() != null && !request.getChannel().isBlank()) {
            c.setChannel(request.getChannel());
        }
        c.setStatus("OPEN");
        Conversation saved = conversationRepository.save(c);

        return new ConversationResponse(
                saved.getId(),
                saved.getPassengerId(),
                saved.getLanguageCode(),
                saved.getChannel(),
                saved.getStatus(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public ConversationResponse getConversation(UUID conversationId) {
        Conversation c = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("conversation not found: " + conversationId));

        return new ConversationResponse(
                c.getId(),
                c.getPassengerId(),
                c.getLanguageCode(),
                c.getChannel(),
                c.getStatus(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<ConversationResponse> listConversations(UUID passengerId, int page, int size) {
        PageRequest pr = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 100));
        Page<Conversation> conversations;
        if (passengerId != null) {
            conversations = conversationRepository.findByPassengerIdOrderByUpdatedAtDesc(passengerId, pr);
        } else {
            conversations = conversationRepository.findAll(pr);
        }
        return conversations.map(c -> new ConversationResponse(
                c.getId(),
                c.getPassengerId(),
                c.getLanguageCode(),
                c.getChannel(),
                c.getStatus(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        ));
    }

    @Transactional
    public MessageResponse postMessage(UUID conversationId, PostMessageRequest request) {
        // Ensure conversation exists
        conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("conversation not found: " + conversationId));

        Message m = new Message();
        m.setConversationId(conversationId);
        m.setRole(request.getRole());
        if (request.getMessageType() != null && !request.getMessageType().isBlank()) {
            m.setMessageType(request.getMessageType());
        }
        m.setContent(request.getContent());
        m.setPayload(request.getPayload());

        Message saved = messageRepository.save(m);

        // In real implementation we'd enqueue LLM processing and append BOT reply.
        return new MessageResponse(
                saved.getId(),
                saved.getConversationId(),
                saved.getRole(),
                saved.getMessageType(),
                saved.getContent(),
                saved.getPayload(),
                saved.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> getMessages(UUID conversationId, OffsetDateTime after, int limit) {
        // Ensure conversation exists
        conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("conversation not found: " + conversationId));

        int cappedLimit = Math.min(Math.max(limit, 1), 200);

        List<Message> messages;
        if (after != null) {
            messages = messageRepository.findByConversationIdAndCreatedAtAfterOrderByCreatedAtAsc(
                    conversationId,
                    after,
                    PageRequest.of(0, cappedLimit)
            );
        } else {
            messages = messageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId);
            if (messages.size() > cappedLimit) {
                messages = messages.subList(messages.size() - cappedLimit, messages.size());
            }
        }

        return messages.stream()
                .map(m -> new MessageResponse(
                        m.getId(),
                        m.getConversationId(),
                        m.getRole(),
                        m.getMessageType(),
                        m.getContent(),
                        m.getPayload(),
                        m.getCreatedAt()
                ))
                .toList();
    }
}
