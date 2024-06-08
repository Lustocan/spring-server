package it.example.lavoretti.repository;

import it.example.lavoretti.dao.announcements.AnnouncementEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, UUID> {

}
