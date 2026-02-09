package com.example.intelligentchatbotservice.repo;

import com.example.intelligentchatbotservice.domain.Conversation;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Conversation.
 */
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

    Page<Conversation> findByPassengerIdOrderByUpdatedAtDesc(UUID passengerId, Pageable pageable);
}
