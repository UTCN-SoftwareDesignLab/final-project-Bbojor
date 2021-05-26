package com.example.demo.message.model;

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
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User sender;

    @JoinColumn(name = "recipient_id", updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User recipient;

    @Column
    private String message;
}
