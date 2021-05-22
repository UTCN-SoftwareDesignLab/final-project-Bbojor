package com.example.demo.board;

import com.example.demo.TestCreationFactory;
import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BoardServiceIntegrationTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @BeforeEach
    void setUp() { boardRepository.deleteAll();}

    @Test
    void findAll() {

        List<Board> boards = TestCreationFactory.listOf(Board.class);

        boardRepository.saveAll(boards);

        List<BoardDTO> all = boardService.findAll();
        assertEquals(boards.size(), all.size());
    }

    @Test
    void getBoard() {
        Board board = TestCreationFactory.newBoard();
        board = boardRepository.save(board);

        BoardDTO foundBoard = boardService.getBoard(board.getId());

        assertEquals(board.getDescription(), foundBoard.getDescription());
        assertEquals(board.getName(), foundBoard.getName());
    }

    @Test
    public void edit() {
        Board board = TestCreationFactory.newBoard();
        board = boardRepository.save(board);

        BoardDTO boardDTO = TestCreationFactory.newBoardDTO();
        boardService.edit(board.getId(), boardDTO);

        BoardDTO updatedBoard = boardService.getBoard(board.getId());
        assertEquals(boardDTO.getName(), updatedBoard.getName());
        assertEquals(boardDTO.getDescription(), updatedBoard.getDescription());
    }

    @Test
    public void create() {
        BoardDTO boardDTO = TestCreationFactory.newBoardDTO();
        boardDTO = boardService.create(boardDTO);

        List<BoardDTO> boards = boardService.findAll();
        assertTrue(boards.contains(boardDTO));
    }

    @Test
    public void delete() {
        List<Board> boards = TestCreationFactory.listOf(Board.class);

        boardRepository.saveAll(boards);

        boards = boardRepository.findAll();
        int originalSize = boards.size();

        Board boardToDelete = boards.get(0);

        boardService.delete(boardToDelete.getId());

        List<BoardDTO> foundBoards = boardService.findAll();
        assertEquals(originalSize - 1, foundBoards.size());
    }
}
