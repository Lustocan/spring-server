package it.example.lavoretti.exception;

public class EmailNotAvailableException extends RuntimeException {

    public EmailNotAvailableException(String email) {
        super(email);
    }
}
