package com.chatapp.realtimechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for representing a Channel in API responses.
 * This decouples the API layer from the persistence (JPA entity) layer.
 */
@Data
public class ChannelDto {
    private Long id;
    private String name;
    @JsonProperty("isPublic")   // tell Jackson to use "isPublic"
    private boolean isPublic;
    private LocalDateTime createdAt;
}