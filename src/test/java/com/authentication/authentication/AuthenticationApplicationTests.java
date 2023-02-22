package com.authentication.authentication;

import com.authentication.authentication.models.Role;
import com.authentication.authentication.models.User;
import com.authentication.authentication.repositories.RoleRepository;
import com.authentication.authentication.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AuthenticationApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

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
}
