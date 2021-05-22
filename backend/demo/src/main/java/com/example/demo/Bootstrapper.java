package com.example.demo;


import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.RoleRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Value("${app.bootstrapRoles}")
    private Boolean bootstrapRoles;
    @Value("${app.bootstrapUsers}")
    private Boolean bootstrapUsers;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrapRoles) {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
        }

        if(bootstrapUsers) {
            authService.register(SignupRequest.builder()
                    .email("barbu@email.com")
                    .username("bb")
                    .password("Pass123")
                    .roles(Set.of("ADMIN"))
                    .build());

            authService.register(SignupRequest.builder()
                    .email("secretary@email.com")
                    .username("secretary")
                    .password("Pass123")
                    .roles(Set.of("USER"))
                    .build());

            authService.register(SignupRequest.builder()
                    .email("doctor@email.com")
                    .username("doctor")
                    .password("Pass123")
                    .roles(Set.of("MODERATOR"))
                    .build());
        }
    }
}
