package com.example.demo.message;


import com.example.demo.message.dto.ChatFilterRequestDTO;
import com.example.demo.message.dto.ChatMessageDTO;
import com.example.demo.post.dto.PostFilterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(MESSAGES)
public class ChatController {

    private final ChatService chatService;

    @MessageMapping(MESSAGES)
    public void redirectMessage(@Payload @Valid ChatMessageDTO chatMessage) {
        chatService.handleMessage(chatMessage);
    }

    @GetMapping(FILTERED)
    public List<ChatMessageDTO> getFiltered(@ModelAttribute("filter") ChatFilterRequestDTO filter) {
        return chatService.getFiltered(filter);
    }

}
