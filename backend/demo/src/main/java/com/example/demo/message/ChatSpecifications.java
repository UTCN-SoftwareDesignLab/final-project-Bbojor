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
        specification = specification.and(senderId(filter.getSenderId()));
        specification = specification.and(recipientId(filter.getRecipientId()));

        //also grab messages going the other way in order to have complete conversations
        Specification<ChatMessage> specificationReverse = (root, query, criteriaBuilder) -> criteriaBuilder.and();
        specificationReverse = specificationReverse.and(senderId(filter.getRecipientId()));
        specificationReverse = specificationReverse.and(recipientId(filter.getSenderId()));

        return specification.or(specificationReverse);
    }
}