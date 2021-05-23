package com.example.demo.media.mapper;

import com.example.demo.media.dto.MediaDTO;
import com.example.demo.media.model.Media;

import com.example.demo.post.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    @Named("postToId")
    default Long postToId(Post post) {  return post!=null ? post.getId() : null; }

    @Named("idToPost")
    default Post idToPost(Long id) { return id!=null ? Post.builder().id(id).build() : null; }

    @Mapping(source = "post", target = "postId", qualifiedByName = "postToId")
    MediaDTO toDto(Media media);

    @Mapping(source = "postId", target = "post", qualifiedByName = "idToPost")
    Media fromDto(MediaDTO dto);
}
