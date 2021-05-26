package com.example.demo.thread.mapper;

import com.example.demo.board.model.Board;
import com.example.demo.media.dto.MediaDTO;
import com.example.demo.media.model.Media;
import com.example.demo.thread.dto.ForumThreadDTO;
import com.example.demo.thread.model.ForumThread;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ForumThreadMapper {

    @Named("boardToId")
    default Long boardToId(Board board) { return board.getId(); }

    @Named("idToBoard")
    default Board idToBoard(Long id) { return Board.builder().id(id).build(); }

    @Named("userToId")
    default Long userToId(User user) { return user.getId(); }

    @Named("idToUser")
    default User idToUser(Long id) { return User.builder().id(id).build(); }

    @Mapping(source = "user", target = "userId", qualifiedByName = "userToId")
    @Mapping(source = "board", target = "boardId", qualifiedByName = "boardToId")
    @Mapping(target = "media", ignore = true)
    ForumThreadDTO toDto(ForumThread thread);

    @Mapping(source = "boardId", target = "board", qualifiedByName = "idToBoard")
    @Mapping(source = "userId", target = "user", qualifiedByName = "idToUser")
    @Mapping(target = "media", ignore = true)
    ForumThread fromDto(ForumThreadDTO dto);

    @AfterMapping
    default ForumThreadDTO populateMedia(ForumThread forumThread, @MappingTarget ForumThreadDTO.ForumThreadDTOBuilder forumThreadDTO) {
        return forumThreadDTO.media(forumThread.getMedia().stream().
                map(m -> MediaDTO.builder()
                        .id(m.getId())
                        .threadId(m.getThread().getId())
                        .fileName(m.getFileName())
                        .build())
                .collect(Collectors.toSet())
        ).build();
    }

    @AfterMapping
    default ForumThread populateMedia(ForumThreadDTO forumThreadDTO, @MappingTarget ForumThread.ForumThreadBuilder forumThread) {
        return forumThread.media(forumThreadDTO.getMedia().stream().
                map(m -> Media.builder()
                        .id(m.getId())
                        .thread(ForumThread.builder().id(forumThreadDTO.getId()).build())
                        .fileName(m.getFileName())
                        .build())
                .collect(Collectors.toSet())
        ).build();
    }
}
