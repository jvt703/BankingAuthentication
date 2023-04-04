package dev.n1t.authentication.exception;

public class RefreshTokenGenerationException extends RuntimeException {

    public RefreshTokenGenerationException(String user){
        super(String.format("Failed to Generate Refresh token with User %s information", user));
    }
}
