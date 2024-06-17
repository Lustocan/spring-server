package it.example.lavoretti.mapper;

import it.example.lavoretti.dao.announcements.AnnouncementEntity;
import it.example.lavoretti.dao.users.UserEntity;
import it.example.lavoretti.domain.announcements.Announcement;
import it.example.lavoretti.domain.announcements.CreateAnnouncement;
import javax.annotation.ParametersAreNonnullByDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@ParametersAreNonnullByDefault
public class AnnouncementMapper {

    public Announcement toDomain(AnnouncementEntity announcementEntity) {
        return new Announcement(announcementEntity.getId(),
                                announcementEntity.getTitle(),
                                announcementEntity.getDescription(),
                                announcementEntity.getCategoryAnnouncement(),
                                announcementEntity.getBudget(),
                                announcementEntity.getOffers(),
                                announcementEntity.getSelectedOffer());
    }

    public AnnouncementEntity toEntity(Announcement announcement) {
        return new AnnouncementEntity(announcement.id(),
                                      announcement.title(),
                                      announcement.description(),
                                      announcement.categoryAnnouncement(),
                                      announcement.budget());
    }

    public AnnouncementEntity toEntity(CreateAnnouncement announcement) {
        var currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new AnnouncementEntity(announcement, currentUser.getId());
    }

}
