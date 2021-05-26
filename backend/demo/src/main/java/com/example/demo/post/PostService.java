package com.example.demo.post;

import com.example.demo.media.MediaService;
import com.example.demo.media.dto.MediaDTO;
import com.example.demo.media.mapper.MediaMapper;
import com.example.demo.post.dto.PostDTO;
import com.example.demo.post.dto.PostFilterRequestDTO;
import com.example.demo.post.mapper.PostMapper;
import com.example.demo.post.model.Post;
import com.example.demo.thread.dto.ForumThreadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.post.PostSpecifications.specificationFromFilter;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final MediaMapper mediaMapper;
    private final MediaService mediaService;

    private Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: id = " + id));
    }

    public List<PostDTO> findAllFiltered(PostFilterRequestDTO filter) {
        return postRepository.findAll(specificationFromFilter(filter)).stream()
                .map(postMapper::toDto).collect(Collectors.toList());
    }

    public PostDTO create(PostDTO postDTO, MultipartFile[] files) throws IOException {

        if(postDTO.getDate() == null) {
            postDTO.setDate(new Date());
        }

        postDTO = postMapper.
                toDto(postRepository.save(postMapper.fromDto(postDTO)));

        if(files != null ) {
            Set<MediaDTO> mediaDTO =  mediaService.createMultiple(files);
            PostDTO finalPostDTO = postDTO;
            mediaDTO.forEach(m -> m.setPostId(finalPostDTO.getId()));
            postDTO.setMedia(mediaDTO);
            postDTO = postMapper.toDto(
                    postRepository.save(postMapper.fromDto(postDTO))
            );

            postDTO = postMapper.
                    toDto(postRepository.save(postMapper.fromDto(postDTO)));
        }

        return postDTO;

    }

    public PostDTO edit(Long id, PostDTO postDTO) {
        Post oldPost = findById(id);

        oldPost.setText(postDTO.getText());
        oldPost.setMedia(postDTO.getMedia().stream()
                .map(mediaMapper::fromDto).collect(Collectors.toSet())
        );

        return postMapper.toDto(postRepository.save(oldPost));
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

}
