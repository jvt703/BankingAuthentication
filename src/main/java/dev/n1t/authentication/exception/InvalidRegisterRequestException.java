package dev.n1t.authentication.exception;

public class InvalidRegisterRequestException extends RuntimeException {
    public InvalidRegisterRequestException(String message) {
        super(message);
    }
}