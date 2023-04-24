package dev.n1t.authentication.Service;

import dev.n1t.authentication.models.Address;
import dev.n1t.authentication.models.Role;
import dev.n1t.authentication.models.User;
import dev.n1t.authentication.repositories.AddressRepository;
import dev.n1t.authentication.repositories.RoleRepository;
import dev.n1t.authentication.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class DummyDataInitializer {
    private final AddressRepository addressRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public DummyDataInitializer(AddressRepository addressRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.addressRepository = addressRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        Role inputUserRole = Role.builder()
                .roleName("Admin")
                .build();
        Role inputUserRole2 = Role.builder()
                .roleName("User")
                .build();
        Role outputUserRole = roleRepository.save(inputUserRole);
        System.out.println(outputUserRole);
        Role outputUserRole2 = roleRepository.save(inputUserRole2);
        Address inputUserAddress = Address.builder()
                .city("Boston")
                .zipCode("02116")
                .state("Massachusetts")
                .street("70 Worcester St")
                .build();
        Address outputUserAddress = addressRepository.save(inputUserAddress);
        System.out.println(outputUserAddress);
        User inputUser = User.builder()
                .active(true)
                .email("mario.mario@email.com")
                .address(outputUserAddress)
                .firstname("Mario")
                .lastname("Mario")
                .role(outputUserRole2)
                .emailValidated(false)
                .password(passwordEncoder.encode("Mario"))
                .birthDate(Date.valueOf("1981-07-09").getTime()) .build();
        User outputUser = userRepository.save(inputUser);
        System.out.println(outputUser);

    }

}
