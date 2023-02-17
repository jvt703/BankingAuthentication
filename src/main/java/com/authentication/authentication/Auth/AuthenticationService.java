package com.authentication.authentication.Auth;

import com.authentication.authentication.config.JwtService;
import com.authentication.authentication.config.RefreshService;
import com.authentication.authentication.exception.RefreshException;
import com.authentication.authentication.models.RefreshToken;
import com.authentication.authentication.models.User;
import com.authentication.authentication.repositories.RoleRepository;
import com.authentication.authentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final  JwtService jwtService;

    private final  RefreshService refreshService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                //will come from request we will then find role by role repository
                .role(roleRepository.getRoleById( request.getRole()).orElse(null))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
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
                        return new AuthenticationResponse(Token, RequestToken);
                        }
                        ).orElseThrow(()-> new RefreshException(RequestToken, "refresh token is not in database!"));


    }
}
