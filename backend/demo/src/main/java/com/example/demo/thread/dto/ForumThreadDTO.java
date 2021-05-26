package com.example.demo.thread.dto;

import com.example.demo.media.dto.MediaDTO;
import com.example.demo.media.model.Media;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForumThreadDTO {

    private Long id;

    @NotBlank(message = "Thread must have a title")
    private String title;

    @NotNull(message = "Thread must have a board")
    private Long boardId;

    @NotNull(message = "Thread must have a user posting it")
    private Long userId;

    @NotNull(message = "Thread starter must contain some text")
    private String text;

    @Builder.Default
    private Set<MediaDTO> media = new HashSet<>();

}
