package com.authentication.authentication;

import com.authentication.authentication.Auth.AuthenticationRequest;
import com.authentication.authentication.Auth.AuthenticationResponse;
import com.authentication.authentication.Auth.AuthenticationService;
import com.authentication.authentication.Auth.RegisterRequest;
import com.authentication.authentication.DTO.UserWithTokenDTO;
import com.authentication.authentication.models.Role;
import com.authentication.authentication.models.User;
import com.authentication.authentication.repositories.RoleRepository;
import com.authentication.authentication.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationApplicationTests {
//	private final TestRestTemplate restTemplate;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AuthenticationService authenticationService;

//	@Autowired
//	public AuthenticationApplicationTests(TestRestTemplate restTemplate) {
//
//		this.restTemplate = restTemplate;
//	}


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
				.roleId(roleRepository.getRoleById(1).orElse(null))
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
		Role role = new Role("Admin");
		roleRepository.save(role);
		RegisterRequest registerRequest = new RegisterRequest("test","case","test7@email.com","testpass",1);
		UserWithTokenDTO auth = authenticationService.register(registerRequest);
		assertNotNull(auth.AccessToken());
		assertNotNull(auth.RefreshToken());
	}

	@Test
	void checkIfAuthenticateProducesAuthenticationResponse(){
		Role role = new Role("Admin");
		roleRepository.save(role);
		//register to create user first
		RegisterRequest registerRequest = new RegisterRequest("test","case","test6@email.com","testpass",1);
		UserWithTokenDTO register = authenticationService.register(registerRequest);
		//now we try to authenticate the above user
		AuthenticationRequest authenticationRequest = new AuthenticationRequest("test6@email.com","testpass");
		AuthenticationResponse authenticate = authenticationService.authenticate(authenticationRequest);
		assertNotNull(authenticate.getToken());
		assertNotNull(authenticate.getRefreshToken());
		System.out.println(authenticate.getToken());
	}

//	@Test
//	void checkIfUserAuthenticatedRouteAllowsAccess(){
//		RegisterRequest registerRequest = new RegisterRequest("test","case","test6@email.com","testpass",1);
//		AuthenticationResponse register = authenticationService.register(registerRequest);
//		//now we try to authenticate the above user
//		AuthenticationRequest authenticationRequest = new AuthenticationRequest("test6@email.com","testpass");
//		AuthenticationResponse authenticate = authenticationService.authenticate(authenticationRequest);
//		String AuthToken = authenticate.getToken();
//
//	}




}
