package com.example.demo.thread;

import com.example.demo.thread.dto.ForumThreadDTO;
import com.example.demo.thread.dto.ForumThreadFilterRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    private final SmartValidator validator;

    @GetMapping(FILTERED)
    public List<ForumThreadDTO> findAllFiltered(@ModelAttribute("filter") ForumThreadFilterRequestDTO filter) {
        return forumThreadService.findAllFiltered(filter);
    }

    // this is horrible but it's the only way I could get spring to eat some JSON with its images
    @PostMapping
    public ForumThreadDTO create( @RequestParam("thread") String threadString, @RequestParam(value = "files", required = false) MultipartFile[] files) throws IOException, MethodArgumentNotValidException {

        ForumThreadDTO forumThreadDTO = new ObjectMapper().readValue(threadString, ForumThreadDTO.class);

        DataBinder binder = new DataBinder(forumThreadDTO);
        binder.setValidator(validator);
        binder.validate(forumThreadDTO);

        BindingResult result = binder.getBindingResult();

        if(result.hasErrors()) {
            throw new MethodArgumentNotValidException(null , result);
        }
        else
            return forumThreadService.create(forumThreadDTO, files);
    }

    @GetMapping(ENTITY)
    public ForumThreadDTO getForumThread(@PathVariable Long id) { return forumThreadService.getForumThread(id); }

    @PutMapping(ENTITY)
    public ForumThreadDTO edit(@PathVariable Long id, @Valid @RequestBody ForumThreadDTO forumThreadDTO) { return forumThreadService.edit(id, forumThreadDTO); }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) { forumThreadService.delete(id);}
}
