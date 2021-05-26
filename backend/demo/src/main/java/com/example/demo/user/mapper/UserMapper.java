package com.example.demo.user.mapper;



import com.example.demo.media.model.Media;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Named("avatarToId")
    default Long avatarToId(Media avatar) { return avatar.getId(); }

    @Named("avatarToFile")
    default String avatarToFile(Media avatar) { return avatar.getFileName(); }

    @Named("idToAvatar")
    default Media idToAvatar(Long id) { return Media.builder().id(id).build(); }

    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(source = "avatar", target = "avatarId", qualifiedByName = "avatarToId"),
            @Mapping(source = "avatar", target = "avatarFile", qualifiedByName = "avatarToFile")
    })
    UserDTO toDto(User user);

    @Mappings({
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(source = "avatarId", target = "avatar", qualifiedByName = "idToAvatar")
    })
    User fromDto(UserDTO dto);

    @AfterMapping
    default UserDTO populateRoles(User user, @MappingTarget UserDTO.UserDTOBuilder userDTO) {
        return userDTO.roles(user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet())).build();
    }

    @AfterMapping
    default void populateRoles(UserDTO userDTO, @MappingTarget User.UserBuilder user) {
        user.roles(userDTO.getRoles().stream().map(r -> Role.builder().name(ERole.valueOf(r)).build()).collect(Collectors.toSet())).build();
    }
}

