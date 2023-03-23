package dev.n1t.authentication.exception;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException(){
        super(String.format("Unable to Authenticate User"));
    }
}
