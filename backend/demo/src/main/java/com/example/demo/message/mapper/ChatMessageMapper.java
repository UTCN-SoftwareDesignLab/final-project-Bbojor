package com.example.demo.message.mapper;

import com.example.demo.message.dto.ChatMessageDTO;
import com.example.demo.message.model.ChatMessage;
import com.example.demo.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {

    @Named("userToId")
    default Long userToId(User user) { return user.getId(); }

    @Named("idToUser")
    default User idToUser(Long id) { return User.builder().id(id).build(); }

    @Mapping(source = "sender", target = "senderId", qualifiedByName = "userToId")
    @Mapping(source = "recipient", target = "recipientId", qualifiedByName = "userToId")
    ChatMessageDTO toDto(ChatMessage message);

    @Mapping(source = "senderId", target = "sender", qualifiedByName = "idToUser")
    @Mapping(source = "recipientId", target = "recipient", qualifiedByName = "idToUser")
    ChatMessage fromDto(ChatMessageDTO dto);
}
