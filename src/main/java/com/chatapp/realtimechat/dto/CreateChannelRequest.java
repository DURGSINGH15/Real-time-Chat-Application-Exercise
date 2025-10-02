package com.chatapp.realtimechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO for the request body when creating a new channel.
 */
@Data // Lombok: Generates getters, setters, toString(), etc.
public class CreateChannelRequest {
    private String name;

    @JsonProperty("isPublic")   // tell Jackson to use "isPublic"
    private boolean isPublic; // To distinguish between public and private channels
}