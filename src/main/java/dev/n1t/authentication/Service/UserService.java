package dev.n1t.authentication.Service;

import dev.n1t.authentication.Auth.AuthenticationResponse;
import dev.n1t.authentication.DTO.UserDTO;
import dev.n1t.authentication.DTO.UserWithTokenDTO;
import dev.n1t.authentication.models.User;
import dev.n1t.authentication.repositories.RoleRepository;
import dev.n1t.authentication.repositories.UserRepository;
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
    public UserWithTokenDTO createUserWithTokenDTO(User user, AuthenticationResponse authenticationResponse) {
        if (authenticationResponse == null || authenticationResponse.getToken() == null) {
            throw new IllegalArgumentException("AuthenticationResponse with a non-null token is required");
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        UserWithTokenDTO userWithTokenDTO = new UserWithTokenDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
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
                    user.getFirstname(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getPassword()
            );
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

//    private User userMapper(UserDTO userDTO) {
//        var user = User.builder()
//                .firstName(userDTO.firstname())
//                .lastName(userDTO.lastname())
//                .email(userDTO.email())
//                .emailValidated(false)
//                .password(userDTO.password())
//                .roleId(roleRepository.getRoleById(1).orElse(null))
//                .active(false)
//                .addressId(0)
//                .build();
//        return user;
//    }
//

}
