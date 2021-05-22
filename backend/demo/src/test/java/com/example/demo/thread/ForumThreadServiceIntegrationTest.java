package com.example.demo.thread;

import com.example.demo.TestCreationFactory;
import com.example.demo.board.BoardRepository;
import com.example.demo.board.model.Board;
import com.example.demo.thread.dto.ForumThreadDTO;
import com.example.demo.thread.dto.ForumThreadFilterRequestDTO;
import com.example.demo.thread.model.ForumThread;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ForumThreadServiceIntegrationTest {

    @Autowired
    private ForumThreadRepository forumThreadRepository;

    @Autowired
    private ForumThreadService forumThreadService;

    @Autowired
    private BoardRepository boardRepository;

    private static Board board1, board2;

    @BeforeAll
    static void createBoards() {
        board1 = TestCreationFactory.newBoard();
        board2 = TestCreationFactory.newBoard();
    }

    @BeforeEach
    void saveBoards() {
        board1 = boardRepository.save(board1);
        board2 = boardRepository.save(board2);
    }

    @AfterEach
    void cleanUp()
    {
        forumThreadRepository.deleteAll();
        boardRepository.deleteAll();
    }


    @Test
    void findAllFiltered() {

        List<ForumThread> forumThreads = TestCreationFactory.listOf(ForumThread.class);
        forumThreads.forEach(t -> t.setBoard(board1));

        forumThreadRepository.saveAll(forumThreads);

        ForumThreadFilterRequestDTO filter = ForumThreadFilterRequestDTO.builder()
                .boardId(board1.getId())
                .build();

        List<ForumThreadDTO> all = forumThreadService.findAllFiltered(filter);
        assertEquals(forumThreads.size(), all.size());
    }

    @Test
    public void edit() {
        ForumThread forumThread = TestCreationFactory.newForumThread();
        forumThread.setBoard(board1);
        forumThread = forumThreadRepository.save(forumThread);

        ForumThreadDTO forumThreadDTO = TestCreationFactory.newForumThreadDTO();
        forumThreadDTO.setBoardId(board1.getId());
        forumThreadService.edit(forumThread.getId(), forumThreadDTO);

        ForumThreadDTO thread = forumThreadService.getForumThread(forumThread.getId());
        assertEquals(forumThreadDTO.getTitle(), thread.getTitle());
        assertEquals(forumThreadDTO.getBoardId(), thread.getBoardId());
    }

    @Test
    public void getForumThread() {
        ForumThread forumThread = TestCreationFactory.newForumThread();
        forumThread.setBoard(board1);
        forumThread = forumThreadRepository.save(forumThread);

        ForumThreadDTO thread = forumThreadService.getForumThread(forumThread.getId());
        assertEquals(forumThread.getTitle(), thread.getTitle());
        assertEquals(forumThread.getBoard().getId(), thread.getBoardId());
    }

    @Test
    public void create() {
        ForumThreadDTO forumThreadDTO = TestCreationFactory.newForumThreadDTO();
        forumThreadDTO.setBoardId(board1.getId());
        forumThreadDTO = forumThreadService.create(forumThreadDTO);

        ForumThreadDTO thread = forumThreadService.getForumThread(forumThreadDTO.getId());
        assertEquals(forumThreadDTO.getTitle(), thread.getTitle());
        assertEquals(forumThreadDTO.getBoardId(), thread.getBoardId());
    }

    @Test
    public void delete() {
        List<ForumThread> forumThreads = TestCreationFactory.listOf(ForumThread.class);

        forumThreads.forEach(t -> t.setBoard(board1));
        forumThreadRepository.saveAll(forumThreads);

        forumThreads = forumThreadRepository.findAll();
        int originalSize = forumThreads.size();

        ForumThread threadToDelete = forumThreads.get(0);

        forumThreadService.delete(threadToDelete.getId());

        ForumThreadFilterRequestDTO filter = ForumThreadFilterRequestDTO.builder()
                .boardId(board1.getId())
                .build();

        List<ForumThreadDTO> foundBoards = forumThreadService.findAllFiltered(filter);
        assertEquals(originalSize - 1, foundBoards.size());
    }
}
