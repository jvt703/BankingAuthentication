package dev.n1t.authentication.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String email){
        super(String.format("User with email %s not found", email));
    }

}
