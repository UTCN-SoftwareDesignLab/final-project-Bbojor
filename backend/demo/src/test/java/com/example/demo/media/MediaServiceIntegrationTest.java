package com.example.demo.media;

import com.example.demo.TestCreationFactory;
import com.example.demo.media.dto.MediaDTO;
import com.example.demo.media.model.Media;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.example.demo.Constants.MEDIA_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MediaServiceIntegrationTest {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaService mediaService;

    @BeforeEach
    void setUp() { mediaRepository.deleteAll();}

    @Test
    void getMedia() {
        Media media = TestCreationFactory.newMedia();
        media = mediaRepository.save(media);

        MediaDTO foundMedia = mediaService.findById(media.getId());

        assertEquals(media.getFileName(), foundMedia.getFileName());
    }

    @Test
    public void create() throws IOException {

        MultipartFile file = TestCreationFactory.newMultipartFile();
        MediaDTO mediaDTO = mediaService.create(file);

        MediaDTO foundMedia = mediaService.findById(mediaDTO.getId());
        assertEquals(mediaDTO.getFileName(), foundMedia.getFileName());

        File createdFile = new File(MEDIA_PATH + mediaDTO.getFileName());
        assertTrue(createdFile.exists());
    }

    @Test
    public void delete() {
        List<Media> media = TestCreationFactory.listOf(Media.class);

        mediaRepository.saveAll(media);

        media = mediaRepository.findAll();
        int originalSize = media.size();

        Media mediaToDelete = media.get(0);

        mediaService.delete(mediaToDelete.getId());

        List<Media> foundMedia = mediaRepository.findAll();
        assertEquals(originalSize - 1, foundMedia.size());
    }
}
