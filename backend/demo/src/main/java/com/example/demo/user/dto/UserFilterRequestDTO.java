package com.example.demo.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class UserFilterRequestDTO {

    @Builder.Default
    private final String username = "";
}
