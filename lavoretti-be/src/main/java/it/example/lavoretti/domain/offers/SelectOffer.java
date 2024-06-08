package it.example.lavoretti.domain.offers;

import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record SelectOffer(UUID selectedOfferId) {

}
