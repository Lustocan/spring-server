package it.example.lavoretti.domain.announcements;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.money.CurrencyUnit;
import org.javamoney.moneta.Money;

@ParametersAreNonnullByDefault
public record CreateAnnouncement(String title,
                                 String description,
                                 CategoryAnnouncementType categoryAnnouncement,
                                 Money budget) {

    @Nonnull
    public CurrencyUnit getCurrency() {
        return budget.getCurrency();
    }
}
