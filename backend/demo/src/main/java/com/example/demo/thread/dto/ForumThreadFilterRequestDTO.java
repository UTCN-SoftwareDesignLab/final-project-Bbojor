package com.example.demo.thread.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class ForumThreadFilterRequestDTO {

    @Builder.Default
    private final Long boardId = null;

    @Builder.Default
    private final String title = null;
}
