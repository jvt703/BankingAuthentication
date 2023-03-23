package dev.n1t.authentication.exception;

public class AuthenticationCredentialException extends RuntimeException {


    public AuthenticationCredentialException(){
        super(String.format("Failed to authenticate user with these credentials"));
    }
}
