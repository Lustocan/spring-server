package it.example.lavoretti.mapper;

import it.example.lavoretti.dao.offers.OfferEntity;
import it.example.lavoretti.domain.offers.Offer;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OfferMapper {

    public Offer toDomain(OfferEntity offerEntity) {
        return new Offer();
    }

    public OfferEntity toEntity(Offer offer) {
        return new OfferEntity();
    }

}
