package it.example.lavoretti.controller;

import it.example.lavoretti.domain.announcements.Announcement;
import it.example.lavoretti.domain.announcements.CreateAnnouncement;
import it.example.lavoretti.domain.offers.SelectOffer;
import it.example.lavoretti.service.announcements.AnnouncementService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody CreateAnnouncement createAnnouncement) {
        Announcement createdAnnouncement = announcementService.createAnnouncement(createAnnouncement);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnouncement);
    }

    @GetMapping
    public ResponseEntity<Page<Announcement>> getAllAnnouncements(@PageableDefault Pageable pageable) {
        Page<Announcement> announcements = announcementService.getAllAnnouncements(pageable);
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable UUID id) {
        Announcement announcement = announcementService.getAnnouncementById(id);
        return ResponseEntity.ok(announcement);
    }

    @PostMapping("/{id}/select-offer")
    public ResponseEntity<Announcement> selectOffer(@PathVariable UUID id, @RequestBody SelectOffer selectOffer) {
        Announcement announcement = announcementService.selectOffer(id, selectOffer);
        return ResponseEntity.ok(announcement);
    }

}
