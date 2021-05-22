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
public class ChatMessage {

    @NotNull(message = "Missing sender id")
    private Long senderID;

    @NotNull(message = "Missing recipient id")
    private Long recipientID;

    @NotBlank(message = "Message must have a body")
    private String message;
}
