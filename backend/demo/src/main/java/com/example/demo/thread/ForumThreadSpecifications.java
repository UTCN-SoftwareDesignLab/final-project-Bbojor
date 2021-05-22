package com.example.demo.thread;

import com.example.demo.thread.dto.ForumThreadFilterRequestDTO;
import com.example.demo.thread.model.ForumThread;
import org.springframework.data.jpa.domain.Specification;

public class ForumThreadSpecifications {

    public static Specification<ForumThread> boardId(Long boardId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("board").get("id"), boardId);
    }

    public static Specification<ForumThread> title(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), title);
    }

    public static Specification<ForumThread> specificationFromFilter(ForumThreadFilterRequestDTO filter) {
        Specification<ForumThread> specification = (root, query, criteriaBuilder) -> criteriaBuilder.and();

        if(filter.getBoardId() != null) {
            specification = specification.and(boardId(filter.getBoardId()));
        }
        if(filter.getTitle() != null) {
            specification = specification.and(title(filter.getTitle()));
        }
        return specification;
    }
}
