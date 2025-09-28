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

    @Lob // Specifies that this field should be stored as a "Large Object". Good for long text content.
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // --- RELATIONSHIP MAPPING ---

//    Entity having @JoinColumn is the OWNING side
    @ManyToOne(fetch = FetchType.LAZY) // Many messages can be sent by one user.
    // LAZY fetch means the sender is loaded from DB only when accessed.
    @JoinColumn(name = "sender_id", nullable = false) // This creates a 'sender_id' foreign key column in the 'messages' table.
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY) // Many messages can belong to one channel.
    @JoinColumn(name = "channel_id", nullable = false) // This creates a 'channel_id' foreign key column.
    private Channel channel;
}