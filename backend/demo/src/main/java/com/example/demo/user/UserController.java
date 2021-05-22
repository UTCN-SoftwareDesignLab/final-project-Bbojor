package com.example.demo.user;

import com.example.demo.user.dto.PasswordChangeRequest;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.dto.UserFilterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping(FILTERED)
    public List<UserDTO> findAllByRole(@ModelAttribute("filter") UserFilterRequestDTO filter) {
        return userService.findAllByRole(filter.getRole());
    }

    @ModelAttribute("filter")
    public UserFilterRequestDTO getFilter(@RequestParam(value = "role", required = false) String role) {
        return UserFilterRequestDTO.builder()
                .role(role)
                .build();
    }

    @PutMapping(ENTITY)
    public UserDTO edit(@PathVariable Long id ,@Valid @RequestBody UserDTO user) { return userService.edit(id, user); }

    @GetMapping(ENTITY)
    public UserDTO getUser(@PathVariable Long id) { return userService.getUser(id);}

    @PatchMapping(ENTITY)
    public UserDTO changePassword(@PathVariable Long id, @Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        return userService.changePassword(id, passwordChangeRequest.getPassword());
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id) { userService.delete(id);}

}
