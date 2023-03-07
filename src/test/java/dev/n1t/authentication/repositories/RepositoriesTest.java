package dev.n1t.authentication.repositories;

import dev.n1t.authentication.models.RefreshToken;
import dev.n1t.authentication.models.Role;
import dev.n1t.authentication.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoriesTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Test
    void checkIfUserExistsByEmail(){
        Role role = new Role("test");
        roleRepository.save(role);
        var user = User.builder()
                .firstName("test1")
                .lastName("lasttest")
                .email("Test2@email.com")
                .emailValidated(false)
                .password("testpass")
                //will come from request we will then find role by role repository
                .roleId(roleRepository.getRoleById(1).orElse(null))
                .active(false)
                .addressId(0)
                .build();
        userRepository.save(user);

        User testUser = userRepository.findByEmail("Test2@email.com").orElse(null);
        assertEquals(user.getFirstName(),testUser.getFirstName());
        assertEquals(user.getLastName(),testUser.getLastName());
        assertEquals(user.getEmail(),testUser.getEmail());
    }


    @Test
    void checkIfRoleExistsByRoleName(){
        Role role = new Role("test2");
        roleRepository.save(role);
        Role roletest = roleRepository.getRoleByRoleName("test2").orElse(null);
        assertEquals(role.getRoleName(),roletest.getRoleName());
    }

    @Test
    void checkIfRefreshTokenExists(){
        Role role = new Role("test3");
        roleRepository.save(role);
        var user = User.builder()
                .firstName("test")
                .lastName("lasttest")
                .email("Test@email1.com")
                .emailValidated(false)
                .password("testpass")
                //will come from request we will then find role by role repository
                .roleId(roleRepository.getRoleById(1).orElse(null))
                .active(false)
                .addressId(0)
                .build();
        userRepository.save(user);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(1).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(1000l));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        RefreshToken refreshReturn = refreshTokenRepository.findByToken(refreshToken.getToken()).orElse(null);
        assertEquals(refreshReturn.getToken(), refreshToken.getToken());

    }

}
