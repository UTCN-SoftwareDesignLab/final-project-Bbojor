package com.example.demo.chat;

import com.example.demo.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChatRepository extends JpaRepository<ChatMessage, Long>, JpaSpecificationExecutor<ChatMessage> {

}
