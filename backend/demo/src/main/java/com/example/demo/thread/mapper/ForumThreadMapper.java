package com.example.demo.thread.mapper;

import com.example.demo.board.model.Board;
import com.example.demo.thread.dto.ForumThreadDTO;
import com.example.demo.thread.model.ForumThread;
import com.example.demo.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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
    ForumThreadDTO toDto(ForumThread thread);

    @Mapping(source = "boardId", target = "board", qualifiedByName = "idToBoard")
    @Mapping(source = "userId", target = "user", qualifiedByName = "idToUser")
    ForumThread fromDto(ForumThreadDTO dto);
}
