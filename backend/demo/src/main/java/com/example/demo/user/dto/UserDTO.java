package com.example.demo.user.dto;

import com.example.demo.media.model.Media;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "Must specify username")
    private String username;

    @Email(message = "Not a valid email")
    private String email;

    private Media avatar;

    @Builder.Default
    private Set<String> roles = new HashSet<>();
}
