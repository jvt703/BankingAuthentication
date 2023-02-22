package com.authentication.authentication;

import com.authentication.authentication.Auth.AuthenticationResponse;
import com.authentication.authentication.Auth.AuthenticationService;
import com.authentication.authentication.Auth.RegisterRequest;
import com.authentication.authentication.models.Role;
import com.authentication.authentication.models.User;
import com.authentication.authentication.repositories.RoleRepository;
import com.authentication.authentication.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AuthenticationService authenticationService;
	@Test
	void contextLoads() {
	}

	@Test
	void checkIfUserExistsByEmail(){
		User user = User.builder()
				.firstName("test")
				.lastName("lasttest")
				.email("Test@email.com")
				.password("testpass")
				//will come from request we will then find role by role repository
				.role(roleRepository.getRoleById(1).orElse(null))
				.build();
		userRepository.save(user);

		User testUser = userRepository.findByEmail("Test@email.com").orElse(null);
		assertEquals(user.getFirstName(),testUser.getFirstName());
		assertEquals(user.getLastName(),testUser.getLastName());
		assertEquals(user.getEmail(),testUser.getEmail());
	}

	@Test
	void checkIfRoleExistsByRoleName(){
		Role role = new Role("test");
		roleRepository.save(role);
		Role roletest = roleRepository.getRoleByRoleName("test").orElse(null);
		assertEquals(role.getRoleName(),roletest.getRoleName());
	}
	@Test
	void checkIfRegisterProducesAuthenticationResponse(){

		RegisterRequest registerRequest = new RegisterRequest("test","case","test2@email.com","testpass",1);

		AuthenticationResponse auth = authenticationService.register(registerRequest);
		assertNotNull(auth.getToken());
		assertNotNull(auth.getRefreshToken());
		System.out.println(auth.getRefreshToken());
		System.out.println(auth.getToken());
	}


}
