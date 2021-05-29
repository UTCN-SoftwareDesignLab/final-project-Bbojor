package com.example.demo.chat.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ChatFilterRequestDTO {

    @NotNull
    private Long senderId;

    @NotNull
    private Long recipientId;
}
