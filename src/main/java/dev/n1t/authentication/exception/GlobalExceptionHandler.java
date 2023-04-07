package dev.n1t.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@ControllerAdvice

public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(value = AuthenticationCredentialException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationCredentialException(AuthenticationCredentialException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid email/password");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    @ResponseBody
    @ExceptionHandler(value = RefreshTokenGenerationException.class)
    public ResponseEntity<Map<String, String>> handleRefreshTokenGenerationException(RefreshTokenGenerationException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", "Refresh Token Expired");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e){
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
