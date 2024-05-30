package it.example.lavoretti.domain;

import jakarta.annotation.Nonnull;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

public record User(@Nonnull UUID id,
                   @Nonnull RoleType role,
                   @Nonnull String password,
                   @Nonnull String salt,
                   @Nonnull String username,
                   @Nonnull String email,
                   boolean accountExpired,
                   boolean locked,
                   boolean credentialsExpired,
                   boolean enabled,
                   @Nonnull LocalDateTime expiredAt) {

}
