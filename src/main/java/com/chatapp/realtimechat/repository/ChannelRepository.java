package com.chatapp.realtimechat.repository;

import com.chatapp.realtimechat.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Channel entity.
 * Provides standard CRUD operations for channels.
 */
@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    // You can add custom query methods here later if needed, for example:
    // List<Channel> findByIsPublic(boolean isPublic);
}