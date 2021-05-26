package com.example.demo.post.dto;

import com.example.demo.media.dto.MediaDTO;
import com.example.demo.media.model.Media;
import com.example.demo.thread.model.ForumThread;
import com.example.demo.user.model.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long id;

    @NotBlank(message = "Post must have a body")
    private String text;

    @NotNull(message = "Post must have a user")
    private Long userId;

    @Builder.Default
    private Date date = null;

    @NotNull(message = "Post must belong to a thread")
    private Long threadId;

    @Builder.Default
    private Set<MediaDTO> media = new HashSet<>();
}
