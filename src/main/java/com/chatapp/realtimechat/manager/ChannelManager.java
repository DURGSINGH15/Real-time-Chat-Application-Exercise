package com.chatapp.realtimechat.manager;

import com.chatapp.realtimechat.entity.Channel;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton class to manage active chat channels in-memory.
 * - Stores all active Channel objects.
 * - Ensures only one instance exists across the application.
 * - Provides thread-safe operations.
 */
public class ChannelManager {

    // Step 1: Create the single instance (eager initialization)
    private static final ChannelManager INSTANCE = new ChannelManager();

    // Step 2: Maintain in-memory channel registry
    private final Map<String, Channel> activeChannels = new ConcurrentHashMap<>();

    // Step 3: Private constructor prevents external instantiation
    private ChannelManager() {}

    // Step 4: Provide a global access point
    public static ChannelManager getInstance() {
        return INSTANCE;
    }


    /**
     * Get an existing channel or create a new one if it doesn't exist.
     */
    public Channel getOrCreateChannel(String name) {
        return activeChannels.computeIfAbsent(name, id ->
                Channel.builder()  //using builder pattern to create a new Channel instance instead of setting properties one by one
                        .name(name)
                        .isPublic(true)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    /**
     * Find a channel by name.
     */
    public Optional<Channel> findChannel(String name) {
        return Optional.ofNullable(activeChannels.get(name));
    }

    /**
     * List all currently active channels.
     */
    public Collection<Channel> listChannels() {
        return activeChannels.values();
    }

    /**
     * Remove a channel (e.g., when last user leaves).
     */
    public void removeChannel(String name) {
        activeChannels.remove(name);
    }
}
