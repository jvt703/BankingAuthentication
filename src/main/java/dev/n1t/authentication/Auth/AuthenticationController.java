package dev.n1t.authentication.Auth;


import dev.n1t.authentication.DTO.UserWithTokenDTO;
import dev.n1t.authentication.exception.AuthenticationException;
import dev.n1t.authentication.exception.EmailExistsException;
import dev.n1t.authentication.exception.RefreshTokenGenerationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {

    @Autowired
    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<UserWithTokenDTO> register(@RequestBody @Valid RegisterRequest request) {
        try {
            UserWithTokenDTO response = service.register(request);
            return ResponseEntity.ok(response);
        } catch (EmailExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        try {
            AuthenticationResponse response = service.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody @Valid RefreshRequest request) {
        try {
            AuthenticationResponse response = service.refresh(request);
            return ResponseEntity.ok(response);
        } catch (RefreshTokenGenerationException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
