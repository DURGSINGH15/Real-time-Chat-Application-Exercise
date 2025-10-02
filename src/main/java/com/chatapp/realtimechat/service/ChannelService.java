package com.chatapp.realtimechat.service;

import com.chatapp.realtimechat.dto.CreateChannelRequest;
import com.chatapp.realtimechat.entity.Channel;
import com.chatapp.realtimechat.entity.User;
import com.chatapp.realtimechat.manager.ChannelManager;
import com.chatapp.realtimechat.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;

    /**
     * Creates a new channel and adds the creator as its first member.
     * This operation is transactional, meaning if any part fails, the entire
     * operation will be rolled back, ensuring data consistency.
     *
     * @param request The DTO containing the channel's details.
     * @param creator The authenticated user who is creating the channel.
     * @return The newly persisted Channel entity.
     */
    @Transactional // This is a crucial annotation!
    public Channel createChannel(CreateChannelRequest request, User creator) {
        // 1. Check for duplicate channel names to maintain uniqueness.
        channelRepository.findByName(request.getName()).ifPresent(channel -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A channel with this name already exists.");
        });

        // 2. Build the new Channel entity from the request data.
        Channel newChannel = Channel.builder()
                .name(request.getName())
                .isPublic(request.isPublic())
                .createdAt(LocalDateTime.now())
                .members(Set.of(creator)) // Add the creator as the first member.
                .build();

        // 3. Save the new channel to the database.
        // Because of @Transactional, when we save the channel, Hibernate will also
        // automatically manage the join table entry for the many-to-many relationship
        // between users and channels, adding the creator to the channel.
        Channel saved = channelRepository.save(newChannel);

        // 4. ALSO track it in our Singleton ChannelManager (in-memory registry)
        ChannelManager.getInstance().getOrCreateChannel(saved.getName());

        return saved;
    }

    // --- NEW SERVICE METHOD ---

    /**
     * Retrieves a list of all public channels.
     *
     * @return A list of Channel entities that are marked as public.
     */
    public List<Channel> getPublicChannels() {
        // Call the new repository method to fetch only public channels.
        return channelRepository.findByIsPublic(true);
    }
}