package com.example.demo.thread.dto;

import com.example.demo.board.model.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForumThreadDTO {

    private Long id;

    @NotBlank(message = "Thread must have a title")
    private String title;

    @NotNull(message = "Thread must have a board")
    private Long boardId;
}
