package com.example.demo.board;

import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.mapper.BoardMapper;
import com.example.demo.board.model.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    private Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find board: id = " + id));
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll().stream()
                .map(boardMapper::toDto).collect(Collectors.toList());
    }

    public BoardDTO getBoard(Long id) {
        return boardMapper.toDto(findById(id));
    }

    public BoardDTO edit(Long id, BoardDTO boardDTO) {
        Board oldBoard = findById(id);

        oldBoard.setDescription(boardDTO.getDescription());
        oldBoard.setName(boardDTO.getName());

        return boardMapper.toDto(boardRepository.save(oldBoard));
    }

    public BoardDTO create(BoardDTO boardDTO) {
        return boardMapper.toDto(
                boardRepository.save(boardMapper.fromDto(boardDTO))
        );
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
