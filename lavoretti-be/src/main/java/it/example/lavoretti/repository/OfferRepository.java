package it.example.lavoretti.repository;

import it.example.lavoretti.dao.offers.OfferEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<OfferEntity, UUID> {

    List<OfferEntity> findByAnnouncementId(Long announcementId);
}
