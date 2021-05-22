package com.example.demo.thread.mapper;

import com.example.demo.board.model.Board;
import com.example.demo.thread.dto.ForumThreadDTO;
import com.example.demo.thread.model.ForumThread;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ForumThreadMapper {

    @Named("boardToId")
    default Long boardToId(Board board) { return board.getId(); }

    @Named("idToBoard")
    default Board idToBoard(Long id) { return Board.builder().id(id).build(); }

    @Mapping(source = "board", target = "boardId", qualifiedByName = "boardToId")
    ForumThreadDTO toDto(ForumThread thread);

    @Mapping(source = "boardId", target = "board", qualifiedByName = "idToBoard")
    ForumThread fromDto(ForumThreadDTO dto);
}
