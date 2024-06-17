package it.example.lavoretti.mapper;

import it.example.lavoretti.dao.rating.RatingEntity;
import it.example.lavoretti.domain.rating.CreateRating;
import it.example.lavoretti.domain.rating.Rating;
import it.example.lavoretti.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatingMapper {

    private final UserRepository userRepository;

    public RatingEntity toEntity(CreateRating createRating) {
        var ratedByUser = userRepository.findById(createRating.authorId())
                                        .orElseThrow(() -> new RuntimeException("User not found"));
        var ratedUser = userRepository.findById(createRating.ratedUserId())
                                      .orElseThrow(() -> new RuntimeException("User not found"));
        return new RatingEntity(createRating.rating(),
                                createRating.comment(),
                                ratedByUser,
                                ratedUser);
    }

    public Rating toDomain(RatingEntity ratingEntity) {
        return new Rating();
    }

}
