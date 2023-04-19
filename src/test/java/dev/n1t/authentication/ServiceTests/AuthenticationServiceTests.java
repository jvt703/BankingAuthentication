package dev.n1t.authentication.ServiceTests;

import dev.n1t.authentication.Auth.*;
import dev.n1t.authentication.DTO.UserWithTokenDTO;
import dev.n1t.authentication.Service.UserService;
import dev.n1t.authentication.config.JwtService;
import dev.n1t.authentication.config.RefreshService;
import dev.n1t.authentication.exception.*;

import dev.n1t.authentication.models.RefreshToken;

import dev.n1t.authentication.models.User;
import dev.n1t.authentication.repositories.AddressRepository;
import dev.n1t.authentication.repositories.RefreshTokenRepository;
import dev.n1t.authentication.repositories.RoleRepository;
import dev.n1t.authentication.repositories.UserRepository;
import dev.n1t.model.Address;
import dev.n1t.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTests {


    @Mock
    private UserRepository userRepository;

    @Mock
    RefreshTokenRepository refreshTokenRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;
    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private RefreshService refreshService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserService userService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void Valid_Credentials_should_Return_AuthenticationResponse() {
        // Given
        String email = "user@example.com";
        String password = "password";
        AuthenticationRequest request = new AuthenticationRequest(email, password);
        User user = User.builder()
                .id(1l)
                .email(email)
                .password(password)
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(1);
        refreshToken.setToken("refreshToken");
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now());
        when(refreshService.createRefreshToken(user.getId())).thenReturn(refreshToken);

        // When
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Then
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals("1", response.getId());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(email);
        verify(jwtService).generateToken(user);
        verify(refreshService).createRefreshToken(user.getId());
    }

    @Test
    void Invalid_Credentials_Throws_AuthenticationCredentialException() {
        // Given
        String email = "user@example.com";
        String password = "password";
        AuthenticationRequest request = new AuthenticationRequest(email, password);
        doThrow(new AuthenticationCredentialException()).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // When
        assertThrows(AuthenticationCredentialException.class, () -> authenticationService.authenticate(request));

        // Then
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
    @Test
    void NonexistentUser_Should_Throw_UsernameNotFoundException() {
        // Given
        String email = "user@example.com";
        String password = "password";
        AuthenticationRequest request = new AuthenticationRequest(email, password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When
        assertThrows(UserNotFoundException.class, () -> authenticationService.authenticate(request));

        // Then
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(email);
        verifyNoMoreInteractions(jwtService, refreshService);
    }

    @Test
    void authenticate_withJWTGenerationError_shouldThrowJWTGenerationException() {
        // Given
        String email = "user@example.com";
        String password = "password";
        AuthenticationRequest request = new AuthenticationRequest(email, password);
        User user = User.builder()
                .id(1l)
                .email(email)
                .password(password)
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        doThrow(new JWTGenerationException(user.getUsername())).when(jwtService).generateToken(user);

        // When
        assertThrows(JWTGenerationException.class,  () -> authenticationService.authenticate(request));

        // Then
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(email);
        verify(jwtService).generateToken(user);
        verifyNoMoreInteractions(refreshService);
    }

    @Test
    void authenticate_withRefreshTokenGenerationError_shouldThrowRefreshTokenGenerationException() {
        // Given
        String email = "user@example.com";
        String password = "password";
        AuthenticationRequest request = new AuthenticationRequest(email, password);
        User user = User.builder()
                .id(1l)
                .email(email)
                .password(password)
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        doReturn(null).when(refreshService).createRefreshToken(user.getId());

        // When
        assertThrows(RefreshTokenGenerationException.class, () -> authenticationService.authenticate(request));

        // Then
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(email);
        verify(jwtService).generateToken(user);
        verify(refreshService).createRefreshToken(user.getId());
    }


    @Test
    void register_withValidRequest_shouldReturnUserWithTokenDTO() {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");
        request.setCity("New York");
        request.setState("NY");
        request.setStreet("123 Main St");
        request.setZipCode("10001");
        request.setBirthDate(1234567890L);

        Address address = Address.builder()
                .city(request.getCity())
                .state(request.getState())
                .street(request.getStreet())
                .zipCode(request.getZipCode())
                .build();
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        Role role = new Role();
        role.setId(1l);
        role.setRoleName("ROLE_USER");
        when(roleRepository.getRoleById(1l)).thenReturn(Optional.of(role));

        User user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .emailValidated(false)
                .password("encodedPassword")
                .role(role)
                .active(false)
                .address(address)
                .birthDate(request.getBirthDate())
                .build();
        when(userRepository.save(any(User.class))).thenReturn(user);

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refreshToken");
        when(refreshService.createRefreshToken(user.getId())).thenReturn(refreshToken);

        // When

        AuthenticationResponse authRes = AuthenticationResponse.builder()
                .token("jwtToken")
                .RefreshToken("refreshToken")
                .build();
        UserWithTokenDTO result = userService.createUserWithTokenDTO(user, authRes);

        // Then
        assertNotNull(result);
        assertEquals(request.getFirstName(), result.firstname());
        assertEquals(request.getLastName(), result.lastname());
        assertEquals(request.getEmail(), result.email());
        assertEquals("jwtToken", result.AccessToken());
        assertEquals("refreshToken", result.RefreshToken());
    }
    @Test
    void createUserWithTokenDTO_withNullUser_shouldThrowIllegalArgumentException() {
        // Given
        AuthenticationResponse authRes = AuthenticationResponse.builder()
                .token("jwtToken")
                .RefreshToken("refreshToken")
                .build();

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUserWithTokenDTO(null, authRes);
        });
    }
    @Test
    void createUserWithTokenDTO_withNullAuthResponse_shouldThrowIllegalArgumentException() {
        // Given
        User user = new User();
        user.setId(1l);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
        user.setEmailValidated(false);
        user.setPassword("password");
        user.setRole(new Role(1l, "ROLE_USER"));
        user.setActive(false);
        user.setAddress(new Address(1l, "123 Main St", "New York", "NY", "10001"));
        user.setBirthDate(1234567890L);

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUserWithTokenDTO(user, null);
        });
    }
    @Test
    void createUserWithTokenDTO_withNullTokenInAuthResponse_shouldThrowIllegalArgumentException() {
        // Given
        User user = mock(User.class);
        AuthenticationResponse authRes = AuthenticationResponse.builder().token(null).build();

        // When
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUserWithTokenDTO(user, authRes);
        });

        // Then
        verify(user, never()).getId();
    }
    @Test
    void register_withInvalidRequest_shouldThrowInvalidRequestException() {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("");
        request.setLastName("");
        request.setEmail("");
        request.setPassword("");
        request.setCity("");
        request.setState("");
        request.setStreet("");
        request.setZipCode("");
        request.setBirthDate(0L);

        // When
        assertThrows(InvalidRegisterRequestException.class, () -> authenticationService.register(request));

    }

    @Test
    void refresh_withValidRefreshToken_shouldReturnAuthenticationResponse() {
        // Mocking dependencies
        User user = new User();
        user.setId(1L);
        String token = "valid_token";
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(token)
                .expiryDate(Instant.now().plus(1, ChronoUnit.DAYS))
                .build();

        // Mock the RefreshService to return the refresh token when `findByToken` is called with the valid token value
        when(refreshService.findByToken(eq(token))).thenReturn(Optional.of(refreshToken));
        // Mock the RefreshService to return the refresh token without verifying expiration
        when(refreshService.verifyExpiration(eq(refreshToken))).thenReturn(refreshToken);

        // Mock the JwtService to return a new token when `generateToken` is called with the user
        when(jwtService.generateToken(eq(user))).thenReturn("new_token");

        // Mock the RefreshService to return the same refresh token when `createRefreshToken` is called with the user ID
        when(refreshService.createRefreshToken(eq(user.getId()))).thenReturn(refreshToken);

        // Invoke the method being tested
        RefreshRequest request = new RefreshRequest(token);
        AuthenticationResponse response = authenticationService.refresh(request);

        // Assertions
        assertNotNull(response);
        assertEquals("new_token", response.getToken());
        assertEquals(token, response.getRefreshToken());
        assertEquals("1", response.getId());
    }
}
