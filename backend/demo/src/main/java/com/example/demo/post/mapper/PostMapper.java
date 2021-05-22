package com.example.demo.post.mapper;

import com.example.demo.post.dto.PostDTO;
import com.example.demo.post.model.Post;
import com.example.demo.thread.model.ForumThread;
import com.example.demo.user.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Named("userToId")
    default Long userToId(User user) { return user.getId(); }

    @Named("idToUser")
    default User idToUser(Long id) { return User.builder().id(id).build(); }

    @Named("userToName")
    default String userToNamed(User user) { return user.getUsername(); }

    @Named("userToAvatar")
    default String userToAvatar(User user) { return user.getAvatar().getFileName(); }

    @Named("threadToId")
    default Long threadToId(ForumThread thread) { return thread.getId(); }

    @Named("idToThread")
    default ForumThread idToThread(Long id) { return ForumThread.builder().id(id).build(); }

    @Mapping(source = "forumThread", target = "threadId", qualifiedByName = "threadToId")
    @Mapping(source = "user", target = "userId", qualifiedByName = "userToId")
    @Mapping(source = "user", target = "username", qualifiedByName = "userToName")
    @Mapping(source = "user", target = "userAvatar", qualifiedByName = "userToAvatar")
    PostDTO toDto(Post post);

    @Mapping(source = "userId", target = "user", qualifiedByName = "idToUser")
    @Mapping(source = "threadId", target = "forumThread", qualifiedByName = "idToThread")
    Post fromDto(PostDTO dto);

}
