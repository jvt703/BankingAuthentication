package dev.n1t.authentication.Auth;


import dev.n1t.authentication.DTO.AuthResponse;
import dev.n1t.authentication.DTO.ErrorResponse;
import dev.n1t.authentication.DTO.UserWithTokenDTO;
import dev.n1t.authentication.exception.AuthenticationCredentialException;
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
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        try {
            AuthenticationResponse response = service.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (AuthenticationCredentialException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid email/password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<AuthResponse> refresh(@RequestBody @Valid RefreshRequest request) {
        try {
            AuthenticationResponse response = service.refresh(request);
            return ResponseEntity.ok(response);
        } catch (RefreshTokenGenerationException e) {
            ErrorResponse errorResponse = new ErrorResponse("Refresh Token Expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
