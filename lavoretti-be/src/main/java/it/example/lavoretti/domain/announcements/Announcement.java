package it.example.lavoretti.domain.announcements;

import it.example.lavoretti.dao.offers.OfferEntity;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.money.CurrencyUnit;
import org.javamoney.moneta.Money;

@ParametersAreNonnullByDefault
public record Announcement(UUID id, String title, String description, CategoryAnnouncementType categoryAnnouncement, Money budget,
                           @Nullable Set<OfferEntity> offers, @Nullable OfferEntity selectedOffer) {

    @Nonnull
    public CurrencyUnit getCurrency() {
        return budget.getCurrency();
    }
}
