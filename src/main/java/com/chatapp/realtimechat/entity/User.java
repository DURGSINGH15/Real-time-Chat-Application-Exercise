package com.chatapp.realtimechat.entity;

// Import the required Spring Security classes
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data // Lombok: Generates getters, setters, toString(), equals(), and hashCode()
@Builder // Lombok: Provides the builder pattern for object creation
@NoArgsConstructor // Lombok: Generates a no-argument constructor
@AllArgsConstructor // Lombok: Generates a constructor with all arguments
@Entity // Marks this class as a JPA entity, meaning it will be mapped to a database table.
@Table(name = "users") // Specifies the name of the database table. If omitted, the class name is used.
// Implement the UserDetails interface [***CONNECTS our data model(User) with Spring Security framework]
public class User implements UserDetails {

    @Id // Marks this field as the primary key for the table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures the way the ID is generated.
    // IDENTITY is a common choice for auto-incrementing columns.
    private Long id;

    @Column(unique = true, nullable = false) // Specifies that the 'username' column must be unique and cannot be null.
    private String username;

    @Column(nullable = false) // The 'password' column cannot be null.
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false) // Maps to 'created_at' column. It cannot be null or updated after creation.
    private LocalDateTime createdAt;

    // --- RELATIONSHIP MAPPING ---

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // Lombok: ensures the set is initialized when using the builder
    @EqualsAndHashCode.Exclude // Lombok: Exclude from equals/hashCode to prevent infinite loops
    @ToString.Exclude // Lombok: Exclude from toString to prevent infinite loops
    private Set<Message> messages = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_channels", // Name of the intermediate join table
            joinColumns = @JoinColumn(name = "user_id"), // Foreign key for the User in the join table
            inverseJoinColumns = @JoinColumn(name = "channel_id") // Foreign key for the other side (Channel)
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Channel> channels = new HashSet<>();

    // --- UserDetails Method Implementations ---

    /**
     * Returns the authorities granted to the user. For now, we are not using roles,
     * so we return an empty collection.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // In a real application, you would map roles (e.g., "ROLE_USER", "ROLE_ADMIN") here.
        return Collections.emptyList();
    }

    /**
     * getPassword() is already implemented by Lombok's @Data annotation on the 'password' field.
     */

    /**
     * getUsername() is already implemented by Lombok's @Data annotation on the 'username' field.
     */

    @Override
    public boolean isAccountNonExpired() {
        // For our app, accounts do not expire.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // We are not implementing account locking for now.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Passwords do not expire in our app.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // All created accounts are enabled by default.
        return true;
    }

}