package it.example.lavoretti.domain.auth;


import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record AuthRequest(String username,
                          String password) {

}


