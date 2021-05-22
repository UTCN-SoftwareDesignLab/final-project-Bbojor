package com.example.demo.user;

import com.example.demo.TestCreationFactory;
import com.example.demo.media.MediaRepository;
import com.example.demo.media.model.Media;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static Media avatar;

    @BeforeAll
    static void setMedia() {
        avatar = TestCreationFactory.newMedia();
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        mediaRepository.deleteAll();
        avatar = mediaRepository.save(avatar);
    }

    @Test
    void findAll() {

        List<User> users = TestCreationFactory.listOf(User.class);
        users.forEach(u -> u.setAvatar(avatar));

        userRepository.saveAll(users);

        List<UserDTO> all = userService.findAll();
        assertEquals(users.size(), all.size());

    }

    @Test
    void findAllByRole() {
        List<User> users = TestCreationFactory.listOf(User.class);
        users.forEach(u -> u.setAvatar(avatar));

        HashSet<Role> doctorRole = new HashSet<>();
        Optional<Role> mod = roleRepository.findByName(ERole.MODERATOR);
        doctorRole.add(mod.get());

        users.get(0).setRoles(doctorRole);
        users.get(1).setRoles(doctorRole);
        users.get(2).setRoles(doctorRole);

        userRepository.saveAll(users);


        List<UserDTO> allDoctors = userService.findAllByRole("MODERATOR");

        assertEquals(3, allDoctors.size());
    }

    @Test
    void getUser() {
        User user = TestCreationFactory.newUser();
        user.setAvatar(avatar);
        user = userRepository.save(user);

        UserDTO foundUser = userService.getUser(user.getId());

        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getRoles(), foundUser.getRoles());
    }

    @Test
    public void edit() {
        User user = TestCreationFactory.newUser();
        user.setAvatar(avatar);
        user = userRepository.save(user);

        UserDTO newUser = TestCreationFactory.newUserDTO();
        newUser.setAvatarId(avatar.getId());
        userService.edit(user.getId(), newUser);

        List<UserDTO> allUsers = userService.findAll();
        assertEquals(1, allUsers.size());
        assertEquals(newUser.getUsername(), allUsers.get(0).getUsername());
        assertEquals(newUser.getEmail(), allUsers.get(0).getEmail());
        assertEquals(newUser.getRoles(), allUsers.get(0).getRoles());
    }

    @Test
    public void delete() {
        List<User> users = TestCreationFactory.listOf(User.class);
        users.forEach(u -> u.setAvatar(avatar));
        int originalSize = users.size();
        userRepository.saveAll(users);

        User userToDelete = userRepository.findByUsername(users.get(0).getUsername())
                .orElseThrow(() -> new RuntimeException("Cannot find user "));

        userService.delete(userToDelete.getId());

        List<UserDTO> foundUsers = userService.findAll();
        assertEquals(originalSize - 1, foundUsers.size());
    }

    @Test
    public void changePassword() {
        User user = TestCreationFactory.newUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAvatar(avatar);
        user = userRepository.save(user);

        String newPassword = TestCreationFactory.randomString();
        UserDTO changedUser = userService.changePassword(user.getId(), newPassword);

        User foundUser =  userRepository.findByUsername(changedUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Cannot find user "));

        assertTrue(passwordEncoder.matches(newPassword, foundUser.getPassword()));
    }
}