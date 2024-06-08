package it.example.lavoretti.domain.users;

import jakarta.annotation.Nonnull;

public record SignUpUser(@Nonnull String password,
                         @Nonnull String username,
                         @Nonnull String email) {

}
