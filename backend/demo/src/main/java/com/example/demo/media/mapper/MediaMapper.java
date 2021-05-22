package com.example.demo.media.mapper;

import com.example.demo.media.dto.MediaDTO;
import com.example.demo.media.model.Media;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    MediaDTO toDto(Media media);
    Media fromDto(MediaDTO dto);
}
