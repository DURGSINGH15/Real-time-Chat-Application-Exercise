package com.chatapp.realtimechat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // Specifies that this field should be stored as a Large Object. Good for long text content.
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}