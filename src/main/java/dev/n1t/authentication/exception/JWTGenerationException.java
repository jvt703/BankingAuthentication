package dev.n1t.authentication.exception;

public class JWTGenerationException extends RuntimeException {
    public JWTGenerationException(String user){
        super(String.format("Failed to Generate JWT token with User %s information", user));
    }
}
