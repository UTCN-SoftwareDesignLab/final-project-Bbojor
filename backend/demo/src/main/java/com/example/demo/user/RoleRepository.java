package com.example.demo.user;

import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole role);
}
