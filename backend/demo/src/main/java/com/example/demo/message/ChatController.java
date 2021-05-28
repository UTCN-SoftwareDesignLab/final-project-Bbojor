package com.example.demo.message;


import com.example.demo.message.dto.ChatFilterRequestDTO;
import com.example.demo.message.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MESSAGES)
public class ChatController {

    private final ChatService chatService;

    @MessageMapping(MESSAGE_REQUESTS)
    public void redirectRequest(@Payload @Valid ChatMessageDTO chatMessage) {
        chatService.handleMessageRequest(chatMessage);
    }

    @MessageMapping(SOCKET_MESSAGES)
    public void redirectMessage(@Payload @Valid ChatMessageDTO chatMessage) {
        chatService.handleMessage(chatMessage);
    }

    @ModelAttribute("filter")
    public ChatFilterRequestDTO getFilter(
            @RequestParam(value = "senderId") Long senderId,
            @RequestParam(value = "recipientId") Long recipientId) {
        return ChatFilterRequestDTO.builder()
                .senderId(senderId)
                .recipientId(recipientId)
                .build();
    }

    @GetMapping(FILTERED)
    public List<ChatMessageDTO> getFiltered(@ModelAttribute("filter") ChatFilterRequestDTO filter) {
        return chatService.getFiltered(filter);
    }

}
