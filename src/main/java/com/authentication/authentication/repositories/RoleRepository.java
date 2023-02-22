package com.authentication.authentication.repositories;

import com.authentication.authentication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> getRoleById(Integer id);
    Optional<Role> getRoleByRoleName(String roleName);;
}
