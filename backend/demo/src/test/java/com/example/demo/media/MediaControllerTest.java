package com.example.demo.media;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.media.dto.MediaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MediaControllerTest extends BaseControllerTest {

    @InjectMocks
    private MediaController controller;

    @Mock
    private MediaService mediaService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new MediaController(mediaService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getMedia() throws Exception {
        MediaDTO mediaDTO = TestCreationFactory.newMediaDTO();
        long id = mediaDTO.getId();

        when(mediaService.findById(id)).thenReturn(mediaDTO);

        ResultActions result = performGetWithPathVariable(MEDIA + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(mediaDTO));
    }

    @Test
    void create() throws Exception {

        MultipartFile file = TestCreationFactory.newMultipartFile();
        MediaDTO media = TestCreationFactory.newMediaDTO();

        when(mediaService.create(file)).thenReturn(media);

        ResultActions result = performPostWithMultipartFile(MEDIA, (MockMultipartFile) file);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(media));
    }

    @Test
    void delete() throws Exception {
        MediaDTO mediaDTO = TestCreationFactory.newMediaDTO();
        long id = mediaDTO.getId();

        doNothing().when(mediaService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(MEDIA + ENTITY, id);
        verify(mediaService,times(1)).delete(id);
        result.andExpect(status().isOk());
    }
}
