package com.example.demo.post;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.post.dto.PostDTO;
import com.example.demo.post.dto.PostFilterRequestDTO;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.SmartValidator;

import java.util.List;

import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest extends BaseControllerTest {

    @InjectMocks
    private PostController controller;

    @Mock
    private PostService postService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new PostController(postService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAllFiltered() throws Exception {

        List<PostDTO> userPosts = TestCreationFactory.listOf(PostDTO.class);

        userPosts.forEach( p ->
                p.setUserId(4L));

        PostFilterRequestDTO userFilter = PostFilterRequestDTO.builder()
                .userId(4L)
                .build();

        when(postService.findAllFiltered(userFilter)).thenReturn(userPosts);

        List<PostDTO> threadPosts = TestCreationFactory.listOf(PostDTO.class);

        threadPosts.forEach( p ->
                p.setThreadId(5L));

        PostFilterRequestDTO boardFilter = PostFilterRequestDTO.builder()
                .threadId(5L)
                .build();

        when(postService.findAllFiltered(boardFilter)).thenReturn(threadPosts);

        ResultActions response = performGetWithModelAttribute(POSTS + FILTERED, Pair.of("filter", userFilter));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userPosts));

        response = performGetWithModelAttribute(POSTS + FILTERED, Pair.of("filter", boardFilter));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(threadPosts));
    }

    @Test
    void edit() throws Exception {
        PostDTO postDTO = TestCreationFactory.newPostDTO();
        long id = postDTO.getId();

        when(postService.edit(id, postDTO)).thenReturn(postDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariable(POSTS + ENTITY, postDTO, id);

        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(postDTO));
    }

    @Test
    void create() throws Exception {
        PostDTO postDTO = TestCreationFactory.newPostDTO();
        when(postService.create(postDTO, null)).thenReturn(postDTO);

        ResultActions result = performPostWithRequestBody(POSTS, postDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(postDTO));
    }

    @Test
    void delete() throws Exception {
        PostDTO postDTO = TestCreationFactory.newPostDTO();
        long id = postDTO.getId();

        doNothing().when(postService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(POSTS + ENTITY, id);
        verify(postService,times(1)).delete(id);
        result.andExpect(status().isOk());
    }

}
