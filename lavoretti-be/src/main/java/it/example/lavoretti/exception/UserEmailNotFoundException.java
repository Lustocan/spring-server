package it.example.lavoretti.exception;

import org.springframework.security.core.AuthenticationException;

public class UserEmailNotFoundException extends AuthenticationException {

    public UserEmailNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserEmailNotFoundException(String msg) {
        super(msg);
    }
}
