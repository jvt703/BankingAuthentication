package com.authentication.authentication.Auth;

import com.authentication.authentication.dto.UserDTO;
import com.authentication.authentication.models.User;
import com.authentication.authentication.repositories.RoleRepository;
import com.authentication.authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    private User userMapper(UserDTO userDTO) {
        return new User(
                userDTO.id(),
                roleRepository.getRoleById(0).orElse(null),
                userDTO.firstname(),
                userDTO.lastname(),
                userDTO.password(),
                userDTO.email(),
                false,
                0
        );
    }

}
