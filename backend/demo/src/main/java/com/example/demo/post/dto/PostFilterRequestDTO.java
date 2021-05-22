package com.example.demo.post.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class PostFilterRequestDTO {

    @Builder.Default
    private final Long threadId = null;

    @Builder.Default
    private final Long userId = null;
}
