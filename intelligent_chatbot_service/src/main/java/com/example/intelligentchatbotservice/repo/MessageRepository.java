package com.example.intelligentchatbotservice.repo;

import com.example.intelligentchatbotservice.domain.Message;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Message.
 */
public interface MessageRepository extends JpaRepository<Message, UUID> {

    List<Message> findByConversationIdOrderByCreatedAtAsc(UUID conversationId);

    List<Message> findByConversationIdAndCreatedAtAfterOrderByCreatedAtAsc(
            UUID conversationId,
            OffsetDateTime after,
            Pageable pageable
    );
}
