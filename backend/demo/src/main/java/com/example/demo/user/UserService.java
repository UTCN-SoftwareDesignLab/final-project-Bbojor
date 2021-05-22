package com.example.demo.user;

import com.example.demo.media.MediaRepository;
import com.example.demo.media.model.Media;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.Constants.DEFAULT_AVATAR;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MediaRepository mediaRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO edit(Long id, UserDTO user) {

        User actualUser = findById(id);
        actualUser.setUsername(user.getUsername());
        actualUser.setEmail(user.getEmail());

        Set<String> rolesStr = user.getRoles();
        Set<Role> roles = new HashSet<>();

        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Cannot find role: " + ERole.USER));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        if(user.getAvatarId() == null) {
            Media defaultAvatar = mediaRepository.findByFileName(DEFAULT_AVATAR);
            user.setAvatarId(defaultAvatar.getId());
        }

        actualUser.setRoles(roles);

        return userMapper.toDto(userRepository.save(actualUser));
    }

    public UserDTO getUser(Long id) {

        return userMapper.toDto(
                findById(id)
        );
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO changePassword(Long id, String newPassword) {
        User user = findById(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDTO> findAllByRole(String role) {
        HashSet<Role> roleSet = new HashSet<>();
        Optional<Role> searchedRole = roleRepository.findByName(Enum.valueOf(ERole.class, role));
        return userRepository.findAllByRolesContaining(
                searchedRole.orElseThrow(() -> new EntityNotFoundException("Role: " + role)))
                        .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
