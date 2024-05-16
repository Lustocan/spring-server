package it.example.lavoretti.exception;

import jakarta.annotation.Nonnull;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(@Nonnull String userNotFound) {
        super(userNotFound);
    }
}
