package com.example.demo.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BoardDTO {

    @Builder.Default
    private Long id = null;

    @NotBlank(message = "Board must have a name")
    private String name;

    @NotBlank(message = "Board must have a description")
    private String description;
}
