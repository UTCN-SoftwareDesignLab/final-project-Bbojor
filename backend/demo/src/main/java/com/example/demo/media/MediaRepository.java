package com.example.demo.media;

import com.example.demo.media.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {

    public Media findByFileName(String fileName);
}
