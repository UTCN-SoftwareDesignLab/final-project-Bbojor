package com.example.demo.post;

import com.example.demo.post.dto.PostFilterRequestDTO;
import com.example.demo.post.model.Post;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecifications {

    public static Specification<Post> threadId(Long threadId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("forumthread").get("id"), threadId);
    }

    public static Specification<Post> userId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Post> specificationFromFilter(PostFilterRequestDTO filter) {
        Specification<Post> specification = (root, query, criteriaBuilder) -> criteriaBuilder.and();

        if(filter.getThreadId() != null) {
            specification = specification.and(threadId(filter.getThreadId()));
        }
        if(filter.getUserId() != null) {
            specification = specification.and(userId(filter.getUserId()));
        }
        return specification;
    }
}
