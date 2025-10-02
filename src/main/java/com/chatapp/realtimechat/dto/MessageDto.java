package com.chatapp.realtimechat.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for representing a Message in API responses.
 */
@Data
public class MessageDto {
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private String senderUsername; // We want to show the username, not the full user object.
}