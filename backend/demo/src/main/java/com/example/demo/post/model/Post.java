package com.example.demo.post.model;

import com.example.demo.media.model.Media;
import com.example.demo.thread.model.ForumThread;
import com.example.demo.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @JoinColumn(name = "user_id", updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "forum_thread_id", updatable = false)
    @ManyToOne(optional = false)
    private ForumThread forumThread;

    @Column
    private Date date;

    @Column
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post",  cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Media> media = new HashSet<>();
}
