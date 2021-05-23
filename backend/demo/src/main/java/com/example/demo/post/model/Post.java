package com.example.demo.post.model;


import com.example.demo.media.model.Media;
import com.example.demo.thread.model.ForumThread;
import com.example.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @JoinColumn(name = "user_id", updatable = false)
    @ManyToOne(optional = false)
    private User user;

    @JoinColumn(name = "forum_thread_id", updatable = false)
    @ManyToOne(optional = false)
    private ForumThread forumThread;

    @Column
    private Date date;

    @Column
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
    @Builder.Default
    private Set<Media> media = new HashSet<>();
}
