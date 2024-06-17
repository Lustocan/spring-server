package it.example.lavoretti.service.announcements;

import it.example.lavoretti.domain.announcements.Announcement;
import it.example.lavoretti.domain.announcements.CreateAnnouncement;
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

    public static final String ANNOUNCEMENT_NOT_FOUND = "Announcement not found";
    public static final String OFFER_NOT_FOUND = "Offer not found";
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;
    private final AnnouncementValidator announcementValidator;
    private final OfferRepository offerRepository;

    public Announcement createAnnouncement(CreateAnnouncement createAnnouncement) {
        announcementValidator.validateAnnouncement(createAnnouncement);
        var announcementEntity = announcementMapper.toEntity(createAnnouncement);
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
                                                       .orElseThrow(() -> new AnnouncementNotFoundException(ANNOUNCEMENT_NOT_FOUND));
        return announcementMapper.toDomain(announcementEntity);
    }

    public Announcement selectOffer(UUID id, SelectOffer selectOffer) {
        var announcementEntity = announcementRepository.findById(id)
                                                       .orElseThrow(() -> new AnnouncementNotFoundException(ANNOUNCEMENT_NOT_FOUND));
        var offerEntity = offerRepository.findById(selectOffer.selectedOfferId())
                                         .orElseThrow(() -> new AnnouncementNotFoundException(OFFER_NOT_FOUND));
        announcementEntity.setSelectedOffer(offerEntity);
        var savedAnnouncementEntity = announcementRepository.save(announcementEntity);
        return announcementMapper.toDomain(savedAnnouncementEntity);
    }

    public Announcement rateAnnouncement(UUID id, SelectOffer selectOffer) {
        var announcementEntity = announcementRepository.findById(id)
                                                       .orElseThrow(() -> new AnnouncementNotFoundException(ANNOUNCEMENT_NOT_FOUND));
        var offerEntity = offerRepository.findById(selectOffer.selectedOfferId())
                                         .orElseThrow(() -> new AnnouncementNotFoundException(OFFER_NOT_FOUND));
        announcementEntity.setSelectedOffer(offerEntity);
        var savedAnnouncementEntity = announcementRepository.save(announcementEntity);
        return announcementMapper.toDomain(savedAnnouncementEntity);
    }
}
