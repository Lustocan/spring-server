package it.example.lavoretti.domain.rating;

import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record CreateRating(String userId,
                           String productId,
                           int rating,
                           String comment,
                           UUID authorId,
                           UUID ratedUserId) {

    public CreateRating {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }

}
