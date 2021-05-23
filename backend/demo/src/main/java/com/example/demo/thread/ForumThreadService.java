package com.example.demo.thread;

import com.example.demo.thread.dto.ForumThreadDTO;
import com.example.demo.thread.dto.ForumThreadFilterRequestDTO;
import com.example.demo.thread.mapper.ForumThreadMapper;
import com.example.demo.thread.model.ForumThread;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.thread.ForumThreadSpecifications.specificationFromFilter;

@Service
@RequiredArgsConstructor
public class ForumThreadService {

    private final ForumThreadRepository forumThreadRepository;
    private final ForumThreadMapper forumThreadMapper;

    private ForumThread findById(Long id) {
        return forumThreadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Thread not found: id = " + id));
    }

    public List<ForumThreadDTO> findAllFiltered(ForumThreadFilterRequestDTO filter) {
        return forumThreadRepository.findAll(specificationFromFilter(filter)).stream()
                .map(forumThreadMapper::toDto).collect(Collectors.toList());
    }

    public ForumThreadDTO create(ForumThreadDTO forumThreadDTO) {
        return forumThreadMapper.toDto(
                forumThreadRepository.save(forumThreadMapper.fromDto(forumThreadDTO))
        );
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
