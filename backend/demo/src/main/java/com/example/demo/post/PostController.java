package com.example.demo.post;

import com.example.demo.post.dto.PostDTO;
import com.example.demo.post.dto.PostFilterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(POSTS)
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping(FILTERED)
    public List<PostDTO> findAllFiltered(@ModelAttribute("filter") PostFilterRequestDTO filter) {
        return postService.findAllFiltered(filter);
    }

    @PostMapping
    public PostDTO create(@Valid @RequestBody PostDTO postDTO) { return postService.create(postDTO); }

    @PutMapping(ENTITY)
    public PostDTO edit(@PathVariable Long id, @Valid @RequestBody PostDTO postDTO) { return postService.edit(id, postDTO); }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) { postService.delete(id);}
}
