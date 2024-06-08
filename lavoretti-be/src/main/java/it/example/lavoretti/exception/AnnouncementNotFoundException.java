package it.example.lavoretti.exception;

public class AnnouncementNotFoundException extends RuntimeException {

    public AnnouncementNotFoundException(String announcementNotFound) {
        super(announcementNotFound);
    }
}
