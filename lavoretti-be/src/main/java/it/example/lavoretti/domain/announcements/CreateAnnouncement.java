package it.example.lavoretti.domain.announcements;

import javax.annotation.ParametersAreNonnullByDefault;
import org.javamoney.moneta.Money;

@ParametersAreNonnullByDefault
public record CreateAnnouncement(String title,
                                 String description,
                                 Money budget,
                                 CategoryAnnouncementType categoryAnnouncement) {

}
