package com.example.demo.post.mapper;

import com.example.demo.post.dto.PostDTO;
import com.example.demo.post.model.Post;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDTO toDto(Post post);
    Post fromDto(PostDTO dto);

}
