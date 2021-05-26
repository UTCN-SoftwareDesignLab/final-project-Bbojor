package com.example.demo.message.dto;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ChatFilterRequestDTO {
    private Long senderId;
    private Long recipientId;
}
