package com.example.demo;

import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.model.Board;
import com.example.demo.media.dto.MediaDTO;
import com.example.demo.media.model.Media;
import com.example.demo.post.dto.PostDTO;
import com.example.demo.post.model.Post;
import com.example.demo.thread.dto.ForumThreadDTO;
import com.example.demo.thread.model.ForumThread;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.model.User;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(Board.class)) {
            supplier = TestCreationFactory::newBoard;
        } else if (cls.equals(BoardDTO.class)) {
            supplier = TestCreationFactory::newBoardDTO;
        } else if (cls.equals(UserDTO.class)) {
            supplier = TestCreationFactory::newUserDTO;
        } else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        } else if (cls.equals(PostDTO.class)) {
            supplier = TestCreationFactory::newPostDTO;
        } else if (cls.equals(Post.class)) {
            supplier = TestCreationFactory::newPost;
        }else if (cls.equals(MediaDTO.class)) {
            supplier = TestCreationFactory::newMediaDTO;
        } else if (cls.equals(Media.class)) {
            supplier = TestCreationFactory::newMedia;
        } else if (cls.equals(ForumThreadDTO.class)) {
            supplier = TestCreationFactory::newForumThreadDTO;
        } else if (cls.equals(ForumThread.class)) {
                supplier = TestCreationFactory::newForumThread;
        } else {
            supplier = () -> new String("Somethin' went wrong.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    public static Board newBoard() {
        return Board.builder()
                .id(randomLong())
                .description(randomString())
                .name(randomString())
                .build();
    }

    public static BoardDTO newBoardDTO() {
        return BoardDTO.builder()
                .id(randomLong())
                .description(randomString())
                .name(randomString())
                .build();
    }

    public static User newUser() {
        return User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .avatar(newMedia())
                .build();
    }

    public static UserDTO newUserDTO() {
        return UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .avatarId(randomLong())
                .build();
    }

    public static ForumThread newForumThread() {
        return ForumThread.builder()
                .id(randomLong())
                .title(randomString())
                .build();
    }

    public static ForumThreadDTO newForumThreadDTO() {
        return ForumThreadDTO.builder()
                .id(randomLong())
                .title(randomString())
                .boardId(randomLong())
                .build();
    }

    public static Post newPost() {
        return Post.builder()
                .id(randomLong())
                .text(randomString())
                .date(new Date())
                .user(newUser())
                .forumThread(newForumThread())
                .build();
    }

    public static PostDTO newPostDTO() {
        return PostDTO.builder()
                .id(randomLong())
                .text(randomString())
                .date(new Date())
                .userId(randomLong())
                .threadId(randomLong())
                .build();
    }

    public static Media newMedia() {
        return Media.builder()
                .id(randomLong())
                .fileName(randomString())
                .build();
    }

    public static MediaDTO newMediaDTO() {
        return MediaDTO.builder()
                .id(randomLong())
                .fileName(randomString())
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static double randomDouble() {
        return new Random().nextDouble();
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 13;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
