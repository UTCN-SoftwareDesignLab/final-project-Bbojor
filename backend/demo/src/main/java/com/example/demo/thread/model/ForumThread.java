package com.example.demo.thread.model;

import com.example.demo.board.model.Board;
import com.example.demo.media.model.Media;
import com.example.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ForumThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @JoinColumn(name = "board_id", updatable = false)
    @ManyToOne(optional = false)
    private Board board;

    @JoinColumn(name = "user_id")
    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private String text;

    @Column
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "thread")
    @Builder.Default
    private Set<Media> media = new HashSet<>();
}
