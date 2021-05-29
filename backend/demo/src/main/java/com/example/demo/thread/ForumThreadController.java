package com.example.demo.thread;

import com.example.demo.thread.dto.ForumThreadDTO;
import com.example.demo.thread.dto.ForumThreadFilterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.example.demo.UrlMapping.*;
import static com.example.demo.UrlMapping.ENTITY;

@RestController
@RequestMapping(THREADS)
@RequiredArgsConstructor
public class ForumThreadController {

    private final ForumThreadService forumThreadService;

    @GetMapping(FILTERED)
    public List<ForumThreadDTO> findAllFiltered(@ModelAttribute("filter") ForumThreadFilterRequestDTO filter) {
        return forumThreadService.findAllFiltered(filter);
    }

    // apparently setting the content type to undefined in the frontend and setting it back to form-data here is what I was missing the first time I tried... pain
    @PostMapping(consumes = {"multipart/form-data"})
    public ForumThreadDTO create(@Valid @RequestPart("thread") ForumThreadDTO forumThreadDTO, @RequestPart(value = "files", required = false) MultipartFile[] files) throws IOException {
        return forumThreadService.create(forumThreadDTO, files);
    }

    @GetMapping(ENTITY)
    public ForumThreadDTO getForumThread(@PathVariable Long id) { return forumThreadService.getForumThread(id); }

    @PutMapping(ENTITY)
    public ForumThreadDTO edit(@PathVariable Long id, @Valid @RequestBody ForumThreadDTO forumThreadDTO) { return forumThreadService.edit(id, forumThreadDTO); }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) { forumThreadService.delete(id);}
}
