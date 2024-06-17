package it.example.lavoretti.repository;

import it.example.lavoretti.dao.announcements.AnnouncementEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, UUID> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Announcement a WHERE a.authorId = ?1 AND a.id = ?2")
    boolean existsByOfferByAuthorAndRated(UUID ratedUserId, UUID selectedOfferUserId);
}
