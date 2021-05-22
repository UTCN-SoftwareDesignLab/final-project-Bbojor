package com.example.demo.user.mapper;



import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "roles", ignore = true)
    })
    UserDTO toDto(User user);

    @Mappings({
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "roles", ignore = true)
    })
    User fromDto(UserDTO dto);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserDTO userDTO) {
        userDTO.setRoles(user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet()));
    }

    @AfterMapping
    default void populateRoles(UserDTO userDTO, @MappingTarget User user) {
        user.setRoles(userDTO.getRoles().stream().map(r -> Role.builder().name(ERole.valueOf(r)).build()).collect(Collectors.toSet()));
    }
}

