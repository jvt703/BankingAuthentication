package dev.n1t.authentication.Auth;

import dev.n1t.authentication.DTO.UserWithTokenDTO;
import dev.n1t.authentication.Service.EmailServiceImpl;
import dev.n1t.authentication.Service.UserService;
import dev.n1t.authentication.config.JwtService;
import dev.n1t.authentication.config.RefreshService;
import dev.n1t.authentication.exception.*;

import dev.n1t.authentication.models.Address;
import dev.n1t.authentication.models.RefreshToken;
import dev.n1t.authentication.models.User;
import dev.n1t.authentication.repositories.AddressRepository;
import dev.n1t.authentication.repositories.RefreshTokenRepository;
import dev.n1t.authentication.repositories.RoleRepository;
import dev.n1t.authentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final  JwtService jwtService;

    private final RefreshService refreshService;
    private final AuthenticationManager authenticationManager;

    private final EmailServiceImpl emailService;
    public UserWithTokenDTO register(RegisterRequest request) {

        var address = Address.builder()
                .city(request.getCity())
                .state(request.getState())
                .street(request.getStreet())
                .zipCode(request.getZipCode())
                .build();
        Address addressSaved = addressRepository.save(address);
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .emailValidated(false)
                .password(passwordEncoder.encode(request.getPassword()))
                //will come from request we will then find role by role repository
                .role(roleRepository.getRoleById(1l).orElse(null))
                .active(false)
                .address(addressSaved)
                .birthDate(request.getBirthDate())
                .build();


        User userSaved = userRepository.save(user);
        if (userSaved == null) {
            throw new InvalidRegisterRequestException("Failed to save user");
        }

        var jwtToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshService.createRefreshToken(user.getId());
        if (refreshToken == null) {
            throw new InvalidRegisterRequestException("Invalid Register Request");
        }
        AuthenticationResponse authRes = AuthenticationResponse
                .builder()
                .token(jwtToken)
                .RefreshToken(refreshToken.getToken())
                .build();
        UserWithTokenDTO userWithTokenDTO = userService.createUserWithTokenDTO(user,authRes);
        return userWithTokenDTO;
    }

    @Transactional
    public void authenticateCreateOTP(AuthenticationRequest request) throws AuthenticationCredentialException, UserNotFoundException, JWTGenerationException, RefreshTokenGenerationException {
        try {
            // Authenticate the user's credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new AuthenticationCredentialException();
        }
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(request.getEmail()));
        //create the OTP and send email here
        Random random = new Random();
        int min = 100000; // Minimum value (inclusive)
        int max = 999999; // Maximum value (inclusive)

        int randomNumber = random.nextInt(max - min + 1) + min;

        user.setOTP(randomNumber);
        userRepository.save(user);
        emailService.sendOTPEmail(user.getEmail(), user.getOTP());

    }




    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequestOTP request) throws AuthenticationCredentialException, UserNotFoundException, JWTGenerationException, RefreshTokenGenerationException {
        System.out.println("we begin authentication");
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(request.getEmail()));
        if (!user.getOTP().equals(request.getPassword())) {
            throw new AuthenticationException();
        }

        user.setOTP(null);
        userRepository.save(user);

        String jwtToken;
        try {
            jwtToken = jwtService.generateToken(user);
        } catch (JWTGenerationException e) {
            throw new JWTGenerationException(user.getId().toString());
        }
        RefreshToken refreshToken = refreshService.createRefreshToken(user.getId());
        if (refreshToken == null) {
            throw new RefreshTokenGenerationException(user.getId().toString());
        }


        return AuthenticationResponse.builder()
                .token(jwtToken)
                .RefreshToken(refreshToken.getToken())
                .id(user.getId().toString())
                .build();

    }

    @Transactional
    public AuthenticationResponse refresh(RefreshRequest request){
        //here we are going to take the request which should have a refresh token and verify it then if it is verified and not expired we will
        //send back a response with a new access token and the current refresh token
        String RequestToken = request.getRefreshToken();

       return refreshService.findByToken(RequestToken)
                .map(refreshService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                        String Token = jwtService.generateToken(user);
                        RefreshToken TokenToDelete = refreshTokenRepository.findByToken(RequestToken).orElse(null);
                        refreshService.deleteRefreshToken(TokenToDelete);
                        RefreshToken NewRefreshToken = refreshService.createRefreshToken(user.getId());
                        return new AuthenticationResponse(Token, NewRefreshToken.getToken(), user.getId().toString());
                        }
                        ).orElseThrow(()-> new RefreshException(RequestToken, "refresh token is not in database!"));
    }
}
