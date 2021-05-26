package com.example.demo.post.mapper;

import com.example.demo.media.dto.MediaDTO;
import com.example.demo.media.model.Media;
import com.example.demo.post.dto.PostDTO;
import com.example.demo.post.model.Post;
import com.example.demo.thread.dto.ForumThreadDTO;
import com.example.demo.thread.model.ForumThread;
import com.example.demo.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Named("userToId")
    default Long userToId(User user) { return user.getId(); }

    @Named("idToUser")
    default User idToUser(Long id) { return User.builder().id(id).build(); }

    @Named("threadToId")
    default Long threadToId(ForumThread thread) { return thread.getId(); }

    @Named("idToThread")
    default ForumThread idToThread(Long id) { return ForumThread.builder().id(id).build(); }

    @Mapping(source = "forumThread", target = "threadId", qualifiedByName = "threadToId")
    @Mapping(source = "user", target = "userId", qualifiedByName = "userToId")
    @Mapping(target = "media", ignore = true)
    PostDTO toDto(Post post);

    @Mapping(source = "userId", target = "user", qualifiedByName = "idToUser")
    @Mapping(source = "threadId", target = "forumThread", qualifiedByName = "idToThread")
    @Mapping(target = "media", ignore = true)
    Post fromDto(PostDTO dto);

    @AfterMapping
    default PostDTO populateMedia(Post post, @MappingTarget PostDTO.PostDTOBuilder postDTO) {
       return postDTO.media(post.getMedia().stream().
                map(m -> MediaDTO.builder()
                        .id(m.getId())
                        .postId(m.getPost().getId())
                        .fileName(m.getFileName())
                        .build())
                .collect(Collectors.toSet())
        ).build();
    }

    @AfterMapping
    default Post populateMedia(PostDTO postDTO, @MappingTarget Post.PostBuilder post) {
        return post.media(postDTO.getMedia().stream()
                .map(m -> Media.builder()
                .id(m.getId())
                .post(Post.builder().id(postDTO.getId()).build())
                .fileName(m.getFileName())
                .build())
                .collect(Collectors.toSet())
        ).build();
    }

}
