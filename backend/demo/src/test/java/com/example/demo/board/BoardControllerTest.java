package com.example.demo.board;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.board.dto.BoardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoardControllerTest extends BaseControllerTest {

    @InjectMocks
    private BoardController controller;

    @Mock
    private BoardService boardService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BoardController(boardService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAll() throws Exception {

        List<BoardDTO> boards = TestCreationFactory.listOf(BoardDTO.class);
        when(boardService.findAll()).thenReturn(boards);

        ResultActions response = mockMvc.perform(get(BOARDS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(boards));
    }

    @Test
    void edit() throws Exception {
        BoardDTO board = TestCreationFactory.newBoardDTO();
        long id = board.getId();

        when(boardService.edit(id, board)).thenReturn(board);

        ResultActions result = performPutWithRequestBodyAndPathVariable(BOARDS + ENTITY, board, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(board));
    }

    @Test
    void getBoard() throws Exception {
        BoardDTO board = TestCreationFactory.newBoardDTO();
        long id = board.getId();

        when(boardService.getBoard(id)).thenReturn(board);

        ResultActions result = performGetWithPathVariable(BOARDS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(board));
    }

    @Test
    void create() throws Exception {
        BoardDTO board = TestCreationFactory.newBoardDTO();
        when(boardService.create(board)).thenReturn(board);

        ResultActions result = performPostWithRequestBody(BOARDS, board);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(board));
    }

    @Test
    void delete() throws Exception {
        BoardDTO board = TestCreationFactory.newBoardDTO();
        long id = board.getId();

        doNothing().when(boardService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(BOARDS + ENTITY, id);
        verify(boardService,times(1)).delete(id);
        result.andExpect(status().isOk());
    }
}
