package com.example.demo.media;

import com.example.demo.media.dto.MediaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.demo.UrlMapping.*;
import static com.example.demo.UrlMapping.ENTITY;

@RestController
@RequestMapping(MEDIA)
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @GetMapping(ENTITY)
    public MediaDTO findById(@PathVariable Long id) {
        return mediaService.findById(id);
    }

    @PostMapping
    public MediaDTO create(@RequestParam("file") MultipartFile file) throws IOException { return mediaService.create(file); }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) { mediaService.delete(id);}
}
