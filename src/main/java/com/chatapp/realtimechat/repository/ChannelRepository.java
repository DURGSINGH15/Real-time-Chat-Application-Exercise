package com.chatapp.realtimechat.repository;

import com.chatapp.realtimechat.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.Optional;
/**
 * Spring Data JPA repository for the Channel entity.
 * Provides standard CRUD operations for channels.
 */
@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    // --- NEW METHOD DECLARATIONS ---

    /**
     * Finds a channel by its unique name.
     * Spring Data JPA implements this method based on its name.
     *
     * @param name The name of the channel.
     * @return An Optional containing the channel if found.
     */
    Optional<Channel> findByName(String name);

    /**
     * Finds all channels based on their public status.
     * This will generate a query like: "SELECT c FROM Channel c WHERE c.isPublic = :isPublic"
     *
     * @param isPublic The public status to filter by (true or false).
     * @return A list of channels matching the criteria.
     */
    List<Channel> findByIsPublic(boolean isPublic);
}