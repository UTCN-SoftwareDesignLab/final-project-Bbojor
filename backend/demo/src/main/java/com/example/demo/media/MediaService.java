package com.example.demo.media;

import com.example.demo.media.dto.MediaDTO;
import com.example.demo.media.mapper.MediaMapper;
import com.example.demo.media.model.Media;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import static com.example.demo.Constants.MEDIA_PATH;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaMapper mediaMapper;
    private final MediaRepository mediaRepository;

    public MediaDTO findById(Long id) {
        return mediaMapper.toDto(
                mediaRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Media not found: id = " + id)
                )
        );
    }

    public MediaDTO create(MultipartFile file) throws IOException {

        String newFilename = file.hashCode() + "_" + new Date().getTime() + "_" + file.getOriginalFilename();
        File savedFile = new File(MEDIA_PATH + newFilename);

        OutputStream os = new FileOutputStream(savedFile);
        os.write(file.getBytes());

        Media media = Media.builder()
                .fileName(newFilename)
                .build();

        return mediaMapper.toDto(
          mediaRepository.save(media)
        );
    }

    public void delete(Long id) {
        mediaRepository.deleteById(id);
    }

}
