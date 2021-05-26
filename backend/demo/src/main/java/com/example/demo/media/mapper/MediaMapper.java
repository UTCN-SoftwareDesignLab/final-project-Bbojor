package com.example.demo.media.mapper;

import com.example.demo.media.dto.MediaDTO;
import com.example.demo.media.model.Media;

import com.example.demo.post.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    MediaDTO toDto(Media media);

    Media fromDto(MediaDTO dto);
}
