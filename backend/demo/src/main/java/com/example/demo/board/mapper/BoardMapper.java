package com.example.demo.board.mapper;

import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.model.Board;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    BoardDTO toDto(Board board);
    Board fromDto(BoardDTO dto);
}
