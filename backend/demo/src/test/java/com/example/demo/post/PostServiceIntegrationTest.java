package com.example.demo.post;

import com.example.demo.TestCreationFactory;
import com.example.demo.board.BoardRepository;
import com.example.demo.board.model.Board;
import com.example.demo.media.MediaRepository;
import com.example.demo.media.model.Media;
import com.example.demo.post.dto.PostDTO;
import com.example.demo.post.dto.PostFilterRequestDTO;
import com.example.demo.post.model.Post;
import com.example.demo.thread.ForumThreadRepository;
import com.example.demo.thread.model.ForumThread;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostServiceIntegrationTest {

    @Autowired
    private ForumThreadRepository forumThreadRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepository mediaRepository;

    private static ForumThread thread1, thread2;
    private static User user1, user2;
    private static Board board;

    private static Media userAvatar;

    @BeforeAll
    static void createEntities() {
        userAvatar = TestCreationFactory.newMedia();
        user1 = TestCreationFactory.newUser();
        user2 = TestCreationFactory.newUser();
        board = TestCreationFactory.newBoard();
        thread1 = TestCreationFactory.newForumThread();
        thread2 = TestCreationFactory.newForumThread();
    }

    @BeforeEach
    void saveEntities() {
        userAvatar = mediaRepository.save(userAvatar);
        user1.setAvatar(userAvatar);
        user2.setAvatar(userAvatar);
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        board = boardRepository.save(board);
        thread1.setBoard(board);
        thread2.setBoard(board);
        thread1 = forumThreadRepository.save(thread1);
        thread2 = forumThreadRepository.save(thread2);
    }

    @AfterEach
    void cleanUp() {
        postRepository.deleteAll();
        forumThreadRepository.deleteAll();
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findAllFiltered() {

        List<Post> posts = TestCreationFactory.listOf(Post.class);
        posts.forEach(p -> p.setUser(user2));
        posts.forEach(p -> p.setForumThread(thread1));

        postRepository.saveAll(posts);

        List<Post> userPosts = TestCreationFactory.listOf(Post.class);
        userPosts.forEach(p -> p.setUser(user1));
        userPosts.forEach(p -> p.setForumThread(thread2));

        postRepository.saveAll(userPosts);

        PostFilterRequestDTO filter = PostFilterRequestDTO.builder()
                .threadId(thread1.getId())
                .build();

        List<PostDTO> all = postService.findAllFiltered(filter);
        assertEquals(posts.size(), all.size());

        PostFilterRequestDTO userFilter = PostFilterRequestDTO.builder()
                .userId(user1.getId())
                .build();

        all = postService.findAllFiltered(userFilter);
        assertEquals(userPosts.size(), all.size());
    }

    @Test
    public void edit() {
        Post post = TestCreationFactory.newPost();
        post.setForumThread(thread1);
        post.setUser(user1);
        post = postRepository.save(post);

        PostDTO postDTO = TestCreationFactory.newPostDTO();
        postDTO.setUserId(user1.getId());
        postDTO.setThreadId(thread1.getId());
        postService.edit(post.getId(), postDTO);

        PostFilterRequestDTO userFilter = PostFilterRequestDTO.builder()
                .userId(user1.getId())
                .build();

        PostDTO foundPost = postService.findAllFiltered(userFilter).get(0);
        assertEquals(postDTO.getText(), foundPost.getText());
        assertEquals(postDTO.getThreadId(), foundPost.getThreadId());
        assertEquals(postDTO.getUserId(), foundPost.getUserId());
    }

    @Test
    public void create() {
        PostDTO postDTO = TestCreationFactory.newPostDTO();
        postDTO.setThreadId(thread1.getId());
        postDTO.setUserId(user1.getId());
        postDTO = postService.create(postDTO);

        PostFilterRequestDTO userFilter = PostFilterRequestDTO.builder()
                .userId(user1.getId())
                .build();

        PostDTO foundPost = postService.findAllFiltered(userFilter).get(0);
        assertEquals(postDTO.getText(), foundPost.getText());
        assertEquals(postDTO.getThreadId(), foundPost.getThreadId());
        assertEquals(postDTO.getUserId(), foundPost.getUserId());
    }

    @Test
    public void delete() {
        List<Post> posts = TestCreationFactory.listOf(Post.class);

        posts.forEach(p -> p.setForumThread(thread1));
        posts.forEach(p -> p.setUser(user1));
        postRepository.saveAll(posts);

        posts = postRepository.findAll();
        int originalSize = posts.size();

        Post postToDelete = posts.get(0);

        postService.delete(postToDelete.getId());

        PostFilterRequestDTO filter = PostFilterRequestDTO.builder()
                .userId(user1.getId())
                .build();

        List<PostDTO> foundPosts = postService.findAllFiltered(filter);
        assertEquals(originalSize - 1, foundPosts.size());
    }


}
