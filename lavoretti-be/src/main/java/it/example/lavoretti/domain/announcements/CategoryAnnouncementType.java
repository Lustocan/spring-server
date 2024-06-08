package it.example.lavoretti.domain.announcements;

import lombok.Getter;

@Getter
public enum CategoryAnnouncementType {
    GARDENING("Gardening"),
    CLEANING("Cleaning"),
    REPAIRS("Repairs"),
    MOVING_AND_HAULING("Moving & Hauling"),
    PAINTING("Painting"),
    PLUMBING("Plumbing"),
    ELECTRICAL("Electrical"),
    ASSEMBLY("Assembly"),
    PET_CARE("Pet Care"),
    TUTORING("Tutoring"),
    IT_SUPPORT("IT Support"),
    HANDYMAN("Handyman"),
    CARPENTRY("Carpentry"),
    LANDSCAPING("Landscaping"),
    EVENT_HELP("Event Help"),
    COOKING_AND_CATERING("Cooking & Catering"),
    CHILD_CARE("Child Care"),
    DELIVERY("Delivery"),
    PERSONAL_AND_ASSISTANCE("Personal Assistance"),
    MISCELLANEOUS("Miscellaneous");

    private final String value;

    CategoryAnnouncementType(String value) {
        this.value = value;
    }

}
