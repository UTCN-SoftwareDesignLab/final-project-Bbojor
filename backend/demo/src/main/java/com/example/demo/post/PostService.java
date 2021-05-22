package com.example.demo.post;

import com.example.demo.post.dto.PostDTO;
import com.example.demo.post.dto.PostFilterRequestDTO;
import com.example.demo.post.mapper.PostMapper;
import com.example.demo.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.post.PostSpecifications.specificationFromFilter;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: id = " + id));
    }

    public List<PostDTO> findAllFiltered(PostFilterRequestDTO filter) {
        return postRepository.findAll(specificationFromFilter(filter)).stream()
                .map(postMapper::toDto).collect(Collectors.toList());
    }

    public PostDTO create(PostDTO postDTO) {
        return postMapper.toDto(
                postRepository.save(postMapper.fromDto(postDTO))
        );
    }

    public PostDTO edit(Long id, PostDTO postDTO) {
        Post oldPost = findById(id);

        oldPost.setText(postDTO.getText());
        oldPost.setMedia(postDTO.getMedia());

        return postMapper.toDto(postRepository.save(oldPost));
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

}
