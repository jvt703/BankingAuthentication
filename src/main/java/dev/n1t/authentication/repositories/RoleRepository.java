package dev.n1t.authentication.repositories;



import dev.n1t.authentication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getRoleById(Long id);
    Optional<Role> getRoleByRoleName(String roleName);;
}
