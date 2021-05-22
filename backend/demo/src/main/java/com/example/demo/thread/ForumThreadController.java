package com.example.demo.thread;

import com.example.demo.thread.dto.ForumThreadDTO;
import com.example.demo.thread.dto.ForumThreadFilterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping
    public ForumThreadDTO create(@Valid @RequestBody ForumThreadDTO forumThreadDTO) {
        System.out.println("sth");
        return forumThreadService.create(forumThreadDTO); }

    @GetMapping(ENTITY)
    public ForumThreadDTO getForumThread(@PathVariable Long id) { return forumThreadService.getForumThread(id);}

    @PutMapping(ENTITY)
    public ForumThreadDTO edit(@PathVariable Long id, @Valid @RequestBody ForumThreadDTO forumThreadDTO) { return forumThreadService.edit(id, forumThreadDTO); }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) { forumThreadService.delete(id);}
}
