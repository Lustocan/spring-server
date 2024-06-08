package it.example.lavoretti.exception;

public class UserNameNotAvailableException extends RuntimeException {

    public UserNameNotAvailableException(String message) {
        super(message);
    }
}
