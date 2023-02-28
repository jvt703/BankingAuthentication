package com.authentication.authentication.Service;

import com.authentication.authentication.Auth.AuthenticationResponse;
import com.authentication.authentication.DTO.UserDTO;
import com.authentication.authentication.DTO.UserWithTokenDTO;
import com.authentication.authentication.models.User;
import com.authentication.authentication.repositories.RoleRepository;
import com.authentication.authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDTO createUserDTO(UserDTO userDTO) {
        ArrayList<User> userList = new ArrayList<>();
        userList.add(createUser(userMapper(userDTO)));
        return userDTOMapper(userList).get(0);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserWithTokenDTO createUserWithTokenDTO(User user, AuthenticationResponse authenticationResponse) {
        UserWithTokenDTO userWithTokenDTO = new UserWithTokenDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                authenticationResponse.getToken(),
                authenticationResponse.getRefreshToken()
        );
        return userWithTokenDTO;

    }


    private List<UserDTO> userDTOMapper(List<User> userList) {
        ArrayList<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO userDTO = new UserDTO(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword()
            );
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    private User userMapper(UserDTO userDTO) {
        var user = User.builder()
                .firstName(userDTO.firstname())
                .lastName(userDTO.lastname())
                .email(userDTO.email())
                .emailValidated(false)
                .password(userDTO.password())
                .roleId(roleRepository.getRoleById(1).orElse(null))
                .active(false)
                .addressId(0)
                .build();
        return user;
    }


}
