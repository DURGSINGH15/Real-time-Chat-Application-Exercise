package com.chatapp.realtimechat.util;

import com.chatapp.realtimechat.entity.Channel;
import com.chatapp.realtimechat.entity.Message;
import com.chatapp.realtimechat.entity.User;
import com.chatapp.realtimechat.repository.ChannelRepository;
import com.chatapp.realtimechat.repository.MessageRepository;
import com.chatapp.realtimechat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * A component that seeds the database with initial data for testing purposes.
 * It implements CommandLineRunner, so its run() method is executed once
 * the application context is loaded.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Only seed data if the database is empty to avoid duplicates on restart
        if (userRepository.count() == 0) {
            System.out.println("Seeding initial data...");

            // 1. Create a test user
            User testUser = User.builder()
                    .username("testuser")
                    .password(passwordEncoder.encode("password"))
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(testUser);

            // 2. Create a public channel
            Channel generalChannel = Channel.builder()
                    .name("general")
                    .isPublic(true)
                    .createdAt(LocalDateTime.now())
                    .members(Set.of(testUser))
                    .build();
            channelRepository.save(generalChannel);

            // 3. Create a batch of messages for the channel
            for (int i = 1; i <= 50; i++) {
                Message message = Message.builder()
                        .content("This is message number " + i + " in the general channel.")
                        .timestamp(LocalDateTime.now().minusMinutes(50 - i)) // Stagger timestamps
                        .sender(testUser)
                        .channel(generalChannel)
                        .build();
                messageRepository.save(message);
            }
            System.out.println("Data seeding complete.");
        }
    }
}