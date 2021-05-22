package com.example.demo.message;


import com.example.demo.message.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import static com.example.demo.UrlMapping.MESSAGE;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping(MESSAGE)
    public void redirectMessage(@Payload ChatMessage chatMessage) {
        chatService.redirectMessage(chatMessage);
    }
}
