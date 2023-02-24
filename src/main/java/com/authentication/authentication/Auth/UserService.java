package com.authentication.authentication.Auth;

import com.authentication.authentication.dto.UserDTO;
import com.authentication.authentication.models.User;
import com.authentication.authentication.repositories.RoleRepository;
import com.authentication.authentication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

//    private final RoleRepository roleRepository;
//    private final UserRepository userRepository;
//    @Autowired
//    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//    }
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//    @Transactional(readOnly = true)
//    public User readUserById(int id) {
//        return userRepository.findById(id).orElse(null);
//    }
//
//    @Transactional(readOnly = true)
//    public List<User> readAllUsers() {
//        return userRepository.findAll();
//    }
//
//    @Transactional
//    public User updateUser(User user) {
//        if (readUserById(user.getId()) == null) {return null;}
//        return userRepository.save(user);
//    }
//
//    @Transactional
//    public boolean deleteUserById(int id) {
//        if (readUserById(id) == null) {return false;}
//        userRepository.deleteById(id);
//        return true;
//    }
//
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public UserDTO createUserDTO(UserDTO userDTO) {
//        ArrayList<User> userList = new ArrayList<>();
//        userList.add(createUser(userMapper(userDTO)));
//        return userDTOMapper(userList).get(0);
//    }
//
//    @Transactional(readOnly = true)
//    public UserDTO readUserByIdDTO(int id) {
//        User user = readUserById(id);
//        if (user == null) {return null;}
//        ArrayList<User> userList = new ArrayList<>();
//        userList.add(user);
//        return userDTOMapper(userList).get(0);
//    }
//
//    @Transactional(readOnly = true)
//    public List<UserDTO> readAllUsersDTO() {
//        return userDTOMapper(readAllUsers());
//    }
//
//    @Transactional
//    public UserDTO updateUserDTO(UserDTO userDTO) {
//        User user = updateUser(userMapper(userDTO));
//        if (user == null) {return null;}
//        ArrayList<User> userList = new ArrayList<>();
//        userList.add(user);
//        return userDTOMapper(userList).get(0);
//    }
//
//    private List<UserDTO> userDTOMapper(List<User> userList) {
//        ArrayList<UserDTO> userDTOList = new ArrayList<>();
//        for (User user : userList) {
//            UserDTO userDTO = new UserDTO(
//                    user.getId(),
//                    user.getFirstName(),
//                    user.getLastName(),
//                    user.getEmail(),
//                    user.getPassword()
//            );
//            userDTOList.add(userDTO);
//        }
//        return userDTOList;
//    }
//
//
//    private User userMapper(UserDTO userDTO) {
//        return new User(
//                userDTO.id(),
//                roleRepository.getRoleById(userDTO.id()).orElse(null),
//                userDTO.firstname(),
//                userDTO.lastname(),
//                userDTO.password(),
//                userDTO.email(),
//                false,
//                0
//        );
//    }

}
