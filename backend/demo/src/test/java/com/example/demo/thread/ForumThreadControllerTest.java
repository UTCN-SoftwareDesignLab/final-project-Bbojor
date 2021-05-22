package com.example.demo.thread;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.board.dto.BoardDTO;
import com.example.demo.thread.dto.ForumThreadDTO;
import com.example.demo.thread.dto.ForumThreadFilterRequestDTO;
import org.apache.commons.lang3.tuple.Pair;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ForumThreadControllerTest extends BaseControllerTest {

    @InjectMocks
    private ForumThreadController controller;

    @Mock
    private ForumThreadService forumThreadService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new ForumThreadController(forumThreadService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAllFiltered() throws Exception {

        List<ForumThreadDTO> gameThreads = TestCreationFactory.listOf(ForumThreadDTO.class);

        gameThreads.forEach( thread ->
                thread.setTitle("something bideo gane something"));


        ForumThreadFilterRequestDTO titleFilter = ForumThreadFilterRequestDTO.builder()
                .title("bideo gane")
                .build();

        when(forumThreadService.findAllFiltered(titleFilter)).thenReturn(gameThreads);

        List<ForumThreadDTO> boardThreads = TestCreationFactory.listOf(ForumThreadDTO.class);

        gameThreads.forEach( thread ->
                thread.setBoardId(5L));

        ForumThreadFilterRequestDTO boardFilter = ForumThreadFilterRequestDTO.builder()
                .boardId(5L)
                .build();

        when(forumThreadService.findAllFiltered(boardFilter)).thenReturn(boardThreads);

        ResultActions response = performGetWithModelAttribute(THREADS + FILTERED, Pair.of("filter", titleFilter));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(gameThreads));

        response = performGetWithModelAttribute(THREADS + FILTERED, Pair.of("filter", boardFilter));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(boardThreads));
    }

    @Test
    void getForumThread() throws Exception {
        ForumThreadDTO forumThreadDTO = TestCreationFactory.newForumThreadDTO();
        long id = forumThreadDTO.getId();

        when(forumThreadService.getForumThread(id)).thenReturn(forumThreadDTO);

        ResultActions response = performGetWithPathVariable(THREADS + ENTITY, id);
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(forumThreadDTO));

    }

    @Test
    void edit() throws Exception {
        ForumThreadDTO forumThreadDTO = TestCreationFactory.newForumThreadDTO();
        long id = forumThreadDTO.getId();

        when(forumThreadService.edit(id, forumThreadDTO)).thenReturn(forumThreadDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariable(THREADS + ENTITY, forumThreadDTO, id);

        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(forumThreadDTO));
    }

    @Test
    void create() throws Exception {
        ForumThreadDTO forumThreadDTO = TestCreationFactory.newForumThreadDTO();
        when(forumThreadService.create(forumThreadDTO)).thenReturn(forumThreadDTO);

        ResultActions result = performPostWithRequestBody(THREADS, forumThreadDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(forumThreadDTO));
    }

    @Test
    void delete() throws Exception {
        ForumThreadDTO forumThreadDTO = TestCreationFactory.newForumThreadDTO();
        long id = forumThreadDTO.getId();

        doNothing().when(forumThreadService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(THREADS + ENTITY, id);
        verify(forumThreadService,times(1)).delete(id);
        result.andExpect(status().isOk());
    }
}
