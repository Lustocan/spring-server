package it.example.lavoretti.domain;

import java.time.LocalDateTime;

public record User(it.example.lavoretti.dao.UserId id,
                   RoleType roleType,
                   String password,
                   String username,
                   String email,
                   boolean accountExpired,
                   boolean locked,
                   boolean credentialsExpired,
                   boolean enabled,
                   LocalDateTime expiredAt) {

}
