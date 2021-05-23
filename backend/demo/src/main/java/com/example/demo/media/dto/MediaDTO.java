package com.example.demo.media.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MediaDTO {

    private Long id;

    private String fileName;

    private Long postId;
}
