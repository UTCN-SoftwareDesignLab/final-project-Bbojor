package com.example.demo.post;

import com.example.demo.post.dto.PostDTO;
import com.example.demo.post.dto.PostFilterRequestDTO;
import com.example.demo.thread.dto.ForumThreadDTO;
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

@RestController
@RequestMapping(POSTS)
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping(FILTERED)
    public List<PostDTO> findAllFiltered(@ModelAttribute("filter") PostFilterRequestDTO filter) {
        return  postService.findAllFiltered(filter);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public PostDTO create(@RequestPart("post") PostDTO postDTO, @RequestPart(value = "files", required = false) MultipartFile[] files) throws IOException {
        return postService.create(postDTO, files);
    }

    @PutMapping(ENTITY)
    public PostDTO edit(@PathVariable Long id, @Valid @RequestBody PostDTO postDTO) { return postService.edit(id, postDTO); }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) { postService.delete(id);}
}
