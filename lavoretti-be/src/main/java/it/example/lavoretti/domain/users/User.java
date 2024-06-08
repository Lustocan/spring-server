package it.example.lavoretti.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import java.time.Instant;
import java.util.UUID;

public record User(@Nonnull UUID id,
                   @Nonnull RoleType role,
                   @Nonnull String password,
                   @JsonIgnore @Nonnull String salt,
                   @Nonnull String username,
                   @Nonnull String email,
                   boolean accountExpired,
                   boolean locked,
                   boolean credentialsExpired,
                   boolean enabled,
                   @Nonnull Instant expiredAt) {

}
