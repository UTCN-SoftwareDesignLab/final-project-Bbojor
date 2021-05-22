package com.example.demo.message;

import com.example.demo.message.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void redirectMessage(ChatMessage chatMessage) {
        simpMessagingTemplate.convertAndSend(
                "/user/" + chatMessage.getRecipientID() + "/" + chatMessage.getSenderID(),
                chatMessage.getMessage());
    }

}
