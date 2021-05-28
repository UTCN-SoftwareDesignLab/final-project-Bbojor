package com.example.demo.message.dto;

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
