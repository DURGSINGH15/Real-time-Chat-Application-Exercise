package com.chatapp.realtimechat.service;

import com.chatapp.realtimechat.entity.Message;
import com.chatapp.realtimechat.repository.ChannelRepository;
import com.chatapp.realtimechat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository; // To verify the channel exists

    /**
     * Retrieves a paginated list of messages for a specific channel.
     *
     * @param channelId The ID of the channel.
     * @param pageable  The pagination information.
     * @return A Page of Message entities.
     */
    public Page<Message> getMessagesForChannel(Long channelId, Pageable pageable) {
        // First, verify that the channel actually exists.
        // If not, throw a 404 error before proceeding.
        if (!channelRepository.existsById(channelId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Channel not found with ID: " + channelId);
        }
        // If the channel exists, fetch the messages.
        return messageRepository.findByChannelIdOrderByTimestampDesc(channelId, pageable);
    }
}