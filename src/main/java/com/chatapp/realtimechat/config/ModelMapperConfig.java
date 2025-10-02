package com.chatapp.realtimechat.config;

import com.chatapp.realtimechat.dto.MessageDto;
import com.chatapp.realtimechat.entity.Message;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Map nested User.username -> senderUsername in DTO
        modelMapper.typeMap(Message.class, MessageDto.class).addMappings(mapper ->
                mapper.map(src -> src.getSender().getUsername(), MessageDto::setSenderUsername)
        );

        return modelMapper;
    }
}
