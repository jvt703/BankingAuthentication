package dev.n1t.authentication.exception;

public class EmailExistsException extends RuntimeException{

    public EmailExistsException(String email){
        super(String.format("email %s not found", email));
    }
}
