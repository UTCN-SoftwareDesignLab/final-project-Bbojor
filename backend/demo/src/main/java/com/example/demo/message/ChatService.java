package com.example.demo.message;

import com.example.demo.message.dto.ChatFilterRequestDTO;
import com.example.demo.message.dto.ChatMessageDTO;
import com.example.demo.message.mapper.ChatMessageMapper;
import com.example.demo.message.model.ChatMessage;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.message.ChatSpecifications.specificationFromFilter;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepository chatRepository;
    private final ChatMessageMapper chatMessageMapper;

    public void handleMessage(ChatMessageDTO chatMessage) {
        System.out.println(chatMessage);
        chatRepository.save(chatMessageMapper.fromDto(chatMessage));
        simpMessagingTemplate.convertAndSend(
                "/user/" + chatMessage.getRecipientId() + "/" + chatMessage.getSenderId(),
                chatMessage);
    }

    public List<ChatMessageDTO> getFiltered(ChatFilterRequestDTO filter) {
        return chatRepository.findAll(specificationFromFilter(filter))
                .stream().map(chatMessageMapper::toDto).collect(Collectors.toList());
    }

    public void handleMessageRequest(ChatMessageDTO chatMessage) {
        System.out.println(chatMessage);
        simpMessagingTemplate.convertAndSend(
                "/user/" + chatMessage.getRecipientId() + "/requests",
                chatMessage);
    }
}
