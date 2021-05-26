package com.example.demo.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChatMessageDTO {

    private Long id;

    @NotNull(message = "Missing sender id")
    private Long senderId;

    @NotNull(message = "Missing recipient id")
    private Long recipientId;

    @NotBlank(message = "Message must have a body")
    private String message;
}
