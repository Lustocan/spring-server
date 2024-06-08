package it.example.lavoretti.service.announcements;

import it.example.lavoretti.domain.announcements.Announcement;
import it.example.lavoretti.domain.offers.SelectOffer;
import it.example.lavoretti.exception.AnnouncementNotFoundException;
import it.example.lavoretti.mapper.AnnouncementMapper;
import it.example.lavoretti.repository.AnnouncementRepository;
import it.example.lavoretti.repository.OfferRepository;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;
    private final AnnouncementValidator announcementValidator;
    private final OfferRepository offerRepository;

    public Announcement createAnnouncement(Announcement announcement) {
        announcementValidator.validateAnnouncement(announcement);
        var announcementEntity = announcementMapper.toEntity(announcement);
        var savedAnnouncementEntity = announcementRepository.save(announcementEntity);
        return announcementMapper.toDomain(savedAnnouncementEntity);
    }

    public Page<Announcement> getAllAnnouncements(Pageable pageable) {
        var announcements = announcementRepository.findAll(pageable)
                                                  .map(announcementMapper::toDomain)
                                                  .toList();
        return new PageImpl<>(announcements, pageable, announcements.size());
    }

    public Announcement getAnnouncementById(UUID id) {
        var announcementEntity = announcementRepository.findById(id)
                                                       .orElseThrow(() -> new AnnouncementNotFoundException("Announcement not found"));
        return announcementMapper.toDomain(announcementEntity);
    }

    public Announcement selectOffer(UUID id, SelectOffer selectOffer) {
        var announcementEntity = announcementRepository.findById(id)
                                                       .orElseThrow(() -> new AnnouncementNotFoundException("Announcement not found"));
        var offerEntity = offerRepository.findById(selectOffer.selectedOfferId())
                                         .orElseThrow(() -> new AnnouncementNotFoundException("Offer not found"));
        announcementEntity.setSelectedOffer(offerEntity);
        var savedAnnouncementEntity = announcementRepository.save(announcementEntity);
        return announcementMapper.toDomain(savedAnnouncementEntity);
    }
}
