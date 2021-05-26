package com.example.demo.thread;

import com.example.demo.media.MediaService;
import com.example.demo.media.dto.MediaDTO;
import com.example.demo.thread.dto.ForumThreadDTO;
import com.example.demo.thread.dto.ForumThreadFilterRequestDTO;
import com.example.demo.thread.mapper.ForumThreadMapper;
import com.example.demo.thread.model.ForumThread;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.thread.ForumThreadSpecifications.specificationFromFilter;

@Service
@RequiredArgsConstructor
public class ForumThreadService {

    private final ForumThreadRepository forumThreadRepository;
    private final ForumThreadMapper forumThreadMapper;
    private final MediaService mediaService;

    private ForumThread findById(Long id) {
        return forumThreadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Thread not found: id = " + id));
    }

    public List<ForumThreadDTO> findAllFiltered(ForumThreadFilterRequestDTO filter) {
        return forumThreadRepository.findAll(specificationFromFilter(filter)).stream()
                .map(forumThreadMapper::toDto).collect(Collectors.toList());
    }

    public ForumThreadDTO create(ForumThreadDTO forumThreadDTO, MultipartFile[] files) throws IOException {

        forumThreadDTO = forumThreadMapper.toDto(
                forumThreadRepository.save(forumThreadMapper.fromDto(forumThreadDTO))
        );

        if(files!= null ) {
            Set<MediaDTO> mediaDTO =  mediaService.createMultiple(files);
            ForumThreadDTO finalForumThreadDTO = forumThreadDTO;
            mediaDTO.forEach(m -> m.setThreadId(finalForumThreadDTO.getId()));
            forumThreadDTO.setMedia(mediaDTO);
            forumThreadDTO = forumThreadMapper.toDto(
                   forumThreadRepository.save(forumThreadMapper.fromDto(forumThreadDTO))
           );
        }

        return forumThreadDTO;
    }

    public ForumThreadDTO edit(Long id, ForumThreadDTO forumThreadDTO) {
        ForumThread oldThread = findById(id);

        oldThread.setTitle(forumThreadDTO.getTitle());

        return forumThreadMapper.toDto(forumThreadRepository.save(oldThread));
    }

    public void delete(Long id) {
        forumThreadRepository.deleteById(id);
    }

    public ForumThreadDTO getForumThread(Long id) {
        return forumThreadMapper.toDto(findById(id));
    }
}
