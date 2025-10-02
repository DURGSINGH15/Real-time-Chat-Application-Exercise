package com.chatapp.realtimechat.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "channels")
public class Channel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "is_public", nullable = false)
    @JsonProperty("isPublic")   // tell Jackson to use "isPublic"
    private boolean isPublic;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // --- RELATIONSHIP MAPPING ---

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Message> messages = new HashSet<>();

    @ManyToMany(mappedBy = "channels", fetch = FetchType.LAZY) // 'mappedBy' indicates this is the non-owning side.
    // The relationship is defined and managed by the 'channels' field in the User entity.
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> members = new HashSet<>();

}