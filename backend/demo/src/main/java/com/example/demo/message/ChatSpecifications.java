package com.example.demo.message;

import com.example.demo.message.dto.ChatFilterRequestDTO;
import com.example.demo.message.model.ChatMessage;
import org.springframework.data.jpa.domain.Specification;

public class ChatSpecifications {

    public static Specification<ChatMessage> senderId(Long senderId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sender").get("id"), senderId);
    }

    public static Specification<ChatMessage> recipientId(Long recipientId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("recipient").get("id"), recipientId);
    }

    public static Specification<ChatMessage> specificationFromFilter(ChatFilterRequestDTO filter) {
        Specification<ChatMessage> specification = (root, query, criteriaBuilder) -> criteriaBuilder.and();

        if(filter.getSenderId() != null) {
            specification = specification.and(senderId(filter.getSenderId()));
        }

        if(filter.getRecipientId() != null) {
            specification = specification.and(recipientId(filter.getRecipientId()));
        }

        return specification;
    }
}