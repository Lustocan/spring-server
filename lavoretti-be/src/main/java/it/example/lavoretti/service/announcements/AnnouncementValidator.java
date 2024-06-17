package it.example.lavoretti.service.announcements;

import it.example.lavoretti.domain.announcements.CreateAnnouncement;
import javax.annotation.ParametersAreNonnullByDefault;
import org.springframework.stereotype.Component;

@Component
@ParametersAreNonnullByDefault
public class AnnouncementValidator {

    public void validateAnnouncement(CreateAnnouncement announcement) {
        if (announcement.budget().isPositive()) {
            throw new IllegalArgumentException("Announcement budget must be positive");
        }
        if (announcement.description().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        if (announcement.title().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        if (announcement.categoryAnnouncement() != null && announcement.categoryAnnouncement().name().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
    }

}
