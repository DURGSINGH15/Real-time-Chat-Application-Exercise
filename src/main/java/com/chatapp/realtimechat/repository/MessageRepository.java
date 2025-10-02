package com.chatapp.realtimechat.repository;

import com.chatapp.realtimechat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA repository for the Message entity.
 * Provides standard CRUD operations for messages.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    /**
     * Finds all messages for a given channel ID, ordered by timestamp in descending order.
     * Spring Data JPA will automatically handle the query generation and apply pagination
     * and sorting based on the provided Pageable object.
     *
     * @param channelId The ID of the channel to fetch messages for.
     * @param pageable The pagination information (page number, size, and sort order).
     * @return A Page object containing a slice of messages and pagination metadata.
     */
    Page<Message> findByChannelIdOrderByTimestampDesc(Long channelId, Pageable pageable);
}