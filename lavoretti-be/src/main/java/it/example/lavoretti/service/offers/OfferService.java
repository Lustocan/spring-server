package it.example.lavoretti.service.offers;

import it.example.lavoretti.domain.offers.Offer;
import it.example.lavoretti.mapper.OfferMapper;
import it.example.lavoretti.repository.AnnouncementRepository;
import it.example.lavoretti.repository.OfferRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final AnnouncementRepository announcementRepository;
    private final OfferMapper offerMapper;

    public Offer createOffer(UUID announcementId, Offer offer) {
        var announcementEntity = announcementRepository.findById(announcementId)
                                                       .orElseThrow(() -> new IllegalArgumentException("Announcement not found"));
        var offerEntity = offerMapper.toEntity(offer);
        offerEntity.setAnnouncementEntity(announcementEntity);
        offerRepository.save(offerEntity);
        return offerMapper.toDomain(offerEntity);
    }

    public Page<Offer> getAllOffers(Pageable page) {
        var offers = offerRepository.findAll(page)
                                    .map(offerMapper::toDomain)
                                    .toList();
        return new PageImpl<>(offers, page, offers.size());
    }

    public Offer getOfferById(UUID id) {
        return offerRepository.findById(id)
                              .map(offerMapper::toDomain)
                              .orElseThrow(() -> new IllegalArgumentException("Offer not found"));
    }
}
