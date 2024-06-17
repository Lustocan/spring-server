package it.example.lavoretti.service.rating;

import it.example.lavoretti.dao.users.UserEntity;
import it.example.lavoretti.domain.rating.CreateRating;
import it.example.lavoretti.repository.AnnouncementRepository;
import javax.validation.ClockProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatingValidator {

    private final AnnouncementRepository announcementRepository;
    private final ClockProvider clockProvider;

    public void validate(CreateRating createRating) {
        var currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isRealAuthor = createRating.authorId().equals(currentUser.getId());
        if (!isRealAuthor) {
            throw new IllegalArgumentException("Rating author must be the current user. Current user: " + currentUser.getId());
        }
        boolean hasAnyWorkAssigned = announcementRepository.existsByOfferByAuthorAndRated(createRating.ratedUserId(),
                                                                                          currentUser.getId());
        if (!hasAnyWorkAssigned) {
            throw new IllegalArgumentException(
                "The current user has not assigned any work to the rated user. Current user: " + currentUser.getId() + ", rated user: "
                    + createRating.ratedUserId());
        }
    }

    public void validateSearchParameters(RatingSearchParameters searchParameters) {
        var maxRangeSearch = clockProvider.getClock().instant().minus(360, java.time.temporal.ChronoUnit.DAYS);
        if (searchParameters.createdFrom() != null && searchParameters.createdFrom().isBefore(maxRangeSearch)) {
            throw new IllegalArgumentException("The search range is too wide. The maximum range is 360 days.");
        }
    }
}
