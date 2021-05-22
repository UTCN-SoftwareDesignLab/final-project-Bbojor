package com.example.demo.post.dto;

import com.example.demo.media.model.Media;
import com.example.demo.thread.model.ForumThread;
import com.example.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long id;

    @NotBlank(message = "Post must have a body")
    private String text;

    @NotNull(message = "Post must have a user")
    private Long userId;

    private String username;

    private String userAvatar;

    @NotNull(message = "Post must have a user")
    private Date date;

    @NotNull(message = "Post must belong to a thread")
    private Long threadId;

    @Builder.Default
    private Set<Media> media = new HashSet<>();
}
