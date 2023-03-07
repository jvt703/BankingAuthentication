package dev.n1t.authentication;

import dev.n1t.authentication.Auth.AuthenticationRequest;
import dev.n1t.authentication.Auth.AuthenticationResponse;
import dev.n1t.authentication.Auth.AuthenticationService;
import dev.n1t.authentication.Auth.RegisterRequest;
import dev.n1t.authentication.DTO.UserWithTokenDTO;
import dev.n1t.authentication.models.Address;
import dev.n1t.authentication.models.Role;
import dev.n1t.authentication.models.User;
import dev.n1t.authentication.repositories.AddressRepository;
import dev.n1t.authentication.repositories.RoleRepository;
import dev.n1t.authentication.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationApplicationTests {
//	private final TestRestTemplate restTemplate;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AddressRepository addressRepository;

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

		var address = Address.builder()
				.city("c")
				.state("d")
				.street("b")
				.zipCode("a")
				.build();
		Role role = new Role("test");
	roleRepository.save(role);
		Address addressSaved = addressRepository.save(address);
		var user = User.builder()
				.firstName("test")
				.lastName("lasttest")
				.email("Test@email.com")
				.emailValidated(false)
				.password("testpass")
				//will come from request we will then find role by role repository
				.roleId(roleRepository.getRoleById(1).orElse(null))
				.active(false)
				.address(addressSaved)
				.build();
		userRepository.save(user);

		User testUser = userRepository.findByEmail("Test@email.com").orElse(null);
		assertEquals(user.getFirstName(),testUser.getFirstName());
		assertEquals(user.getLastName(),testUser.getLastName());
		assertEquals(user.getEmail(),testUser.getEmail());
	}

//	@Test
//	void checkIfRoleExistsByRoleName(){
//		Role role = new Role("test");
//		roleRepository.save(role);
//		Role roletest = roleRepository.getRoleByRoleName("test").orElse(null);
//		assertEquals(role.getRoleName(),roletest.getRoleName());
//	}
//	@Test
//	void checkIfRegisterProducesAuthenticationResponse(){
//		Role role = new Role("Admin");
//		roleRepository.save(role);
//		RegisterRequest registerRequest = new RegisterRequest("test","case","test7@email.com","testpass",1);
//		UserWithTokenDTO auth = authenticationService.register(registerRequest);
//		assertNotNull(auth.AccessToken());
//		assertNotNull(auth.RefreshToken());
//	}
//
//	@Test
//	void checkIfAuthenticateProducesAuthenticationResponse(){
//		Role role = new Role("Admin");
//		roleRepository.save(role);
//		//register to create user first
//		RegisterRequest registerRequest = new RegisterRequest("test","case","test6@email.com","testpass",1);
//		UserWithTokenDTO register = authenticationService.register(registerRequest);
//		//now we try to authenticate the above user
//		AuthenticationRequest authenticationRequest = new AuthenticationRequest("test6@email.com","testpass");
//		AuthenticationResponse authenticate = authenticationService.authenticate(authenticationRequest);
//		assertNotNull(authenticate.getToken());
//		assertNotNull(authenticate.getRefreshToken());
//		System.out.println(authenticate.getToken());
//	}



}
