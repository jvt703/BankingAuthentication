package dev.n1t.authentication.Auth;

import dev.n1t.authentication.DTO.UserWithTokenDTO;
import dev.n1t.authentication.Service.UserService;
import dev.n1t.authentication.config.JwtService;
import dev.n1t.authentication.config.RefreshService;
import dev.n1t.authentication.exception.RefreshException;
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
    public UserWithTokenDTO register(RegisterRequest request) {

        var address = Address.builder()
                .city(request.getCity())
                .state(request.getState())
                .street(request.getStreet())
                .zipCode(request.getZipCode())
                .build();
        Address addressSaved = addressRepository.save(address);
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .emailValidated(false)
                .password(passwordEncoder.encode(request.getPassword()))
                //will come from request we will then find role by role repository
                .roleId(roleRepository.getRoleById( request.getRole()).orElse(null))
                .active(false)
                .address(addressSaved)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = refreshService.createRefreshToken(user.getId());

        AuthenticationResponse authRes = AuthenticationResponse
                .builder()
                .token(jwtToken)
                .RefreshToken(refreshToken.getToken())
                .build();
        UserWithTokenDTO userWithTokenDTO = userService.createUserWithTokenDTO(user,authRes);
        return userWithTokenDTO;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = refreshService.createRefreshToken(user.getId());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .RefreshToken(refreshToken.getToken())
                .build();
    }


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
                        return new AuthenticationResponse(Token, NewRefreshToken.getToken());
                        }
                        ).orElseThrow(()-> new RefreshException(RequestToken, "refresh token is not in database!"));

    }
}
