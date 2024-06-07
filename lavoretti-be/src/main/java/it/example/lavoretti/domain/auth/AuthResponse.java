package it.example.lavoretti.domain.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record AuthResponse(@JsonProperty("access_token")
                           String accessToken) {

}
