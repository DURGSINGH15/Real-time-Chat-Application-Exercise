package com.chatapp.realtimechat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data // Lombok: Generates getters, setters, toString(), equals(), and hashCode()
@Builder // Lombok: Provides the builder pattern for object creation
@NoArgsConstructor // Lombok: Generates a no-argument constructor
@AllArgsConstructor // Lombok: Generates a constructor with all arguments
@Entity // Marks this class as a JPA entity, meaning it will be mapped to a database table.
@Table(name = "users") // Specifies the name of the database table. If omitted, the class name is used.
public class User {

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
}