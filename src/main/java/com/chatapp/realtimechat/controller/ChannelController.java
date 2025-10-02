package com.chatapp.realtimechat.controller;

import com.chatapp.realtimechat.dto.ChannelDto;
import com.chatapp.realtimechat.dto.CreateChannelRequest;
import com.chatapp.realtimechat.entity.Channel;
import com.chatapp.realtimechat.entity.User;
import com.chatapp.realtimechat.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.stream.Collectors;
/**
 * REST controller for managing chat channels.
 * All endpoints in this controller are protected and require a valid JWT for access.
 */
@RestController // Marks this class as a RESTful controller. Spring will automatically handle JSON serialization/deserialization.
@RequestMapping("/api/channels") // Sets the base path for all endpoints in this controller to "/api/channels".
@RequiredArgsConstructor // Lombok annotation to create a constructor for all final fields.
public class ChannelController {

    // --- DEPENDENCY INJECTIONS ---
    private final ChannelService channelService;
    private final ModelMapper modelMapper = new ModelMapper(); // A library to easily map between objects.

    /**
     * Endpoint for an authenticated user to create a new channel.
     *
     * @param request The request body containing the new channel's name and publicity status.
     * @param authentication The Authentication object provided by Spring Security, containing the principal (user).
     * @return A response entity with the details of the newly created channel and a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<ChannelDto> createChannel(
            @RequestBody CreateChannelRequest request,
            Authentication authentication
    ) {
        // 1. Extract the authenticated User from the Authentication principal.
        // This is possible because our JwtRequestFilter sets the fully populated
        // authentication object in the SecurityContext.
        User creator = (User) authentication.getPrincipal();

        // 2. Delegate the business logic to the ChannelService.
        Channel newChannel = channelService.createChannel(request, creator);

        // 3. Map the resulting Channel entity to our ChannelDto for the API response.
        ChannelDto responseDto = modelMapper.map(newChannel, ChannelDto.class);

        // 4. Return the DTO with an HTTP 201 Created status code.
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // --- NEW CONTROLLER ENDPOINT ---

    /**
     * Endpoint to get a list of all public channels.
     * This endpoint is publicly accessible as configured in SecurityConfig.
     *
     * @return A response entity containing a list of public channel DTOs.
     */
    @GetMapping
    public ResponseEntity<List<ChannelDto>> getPublicChannels() {
        // 1. Call the service to get the list of Channel entities.
        List<Channel> publicChannels = channelService.getPublicChannels();

        // 2. Map the list of entities to a list of DTOs for the API response.
        // We use a Java Stream to apply the mapping to each element in the list.
        List<ChannelDto> channelDtos = publicChannels.stream()
                .map(channel -> modelMapper.map(channel, ChannelDto.class))
                .collect(Collectors.toList());

        // 3. Return the list of DTOs with a 200 OK status.
        return ResponseEntity.ok(channelDtos);
    }
}