package com.example.demo.chat;

import com.example.demo.chat.dto.ChatFilterRequestDTO;
import com.example.demo.chat.dto.ChatMessageDTO;
import com.example.demo.chat.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.chat.ChatSpecifications.specificationFromFilter;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepository chatRepository;
    private final ChatMessageMapper chatMessageMapper;

    public void handleMessage(ChatMessageDTO chatMessage) {
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
        simpMessagingTemplate.convertAndSend(
                "/user/" + chatMessage.getRecipientId() + "/requests",
                chatMessage);
    }
}
