package com.example.demo.media.model;

import com.example.demo.post.model.Post;
import com.example.demo.thread.model.ForumThread;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = "fileName")
})
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    @JoinColumn(name = "thread_id")
    @ManyToOne
    private ForumThread thread;

}
