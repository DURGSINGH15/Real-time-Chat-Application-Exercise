package com.chatapp.realtimechat.repository;

import com.chatapp.realtimechat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Message entity.
 * Provides standard CRUD operations for messages.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Later, we might add a method to fetch messages for a specific channel with pagination,
    // like: Page<Message> findByChannelId(Long channelId, Pageable pageable);
}