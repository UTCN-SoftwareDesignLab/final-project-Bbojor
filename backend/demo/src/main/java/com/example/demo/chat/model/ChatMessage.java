package com.example.demo.chat.model;

import com.example.demo.user.model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "sender_id", updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User sender;

    @JoinColumn(name = "recipient_id", updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User recipient;

    @Column
    private String message;
}
