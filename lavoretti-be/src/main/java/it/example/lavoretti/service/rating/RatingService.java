package it.example.lavoretti.service.rating;

import it.example.lavoretti.dao.rating.RatingRepository;
import it.example.lavoretti.domain.rating.CreateRating;
import it.example.lavoretti.domain.rating.Rating;
import it.example.lavoretti.mapper.RatingMapper;
import it.example.lavoretti.repository.UserRepository;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import javax.validation.ClockProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final RatingValidator ratingValidator;
    private final UserRepository userRepository;
    private final ClockProvider clockProvider;

    @Transactional
    public Rating createRating(CreateRating createRating) {
        ratingValidator.validate(createRating);
        var ratedUser = userRepository.findById(createRating.ratedUserId())
                                      .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ratedUser.addNewRating(createRating.rating());
        userRepository.save(ratedUser);
        var ratingEntity = ratingMapper.toEntity(createRating);
        ratingRepository.save(ratingEntity);
        return ratingMapper.toDomain(ratingEntity);
    }

    public Rating getById(UUID id) {
        return ratingRepository.findById(id)
                               .map(ratingMapper::toDomain)
                               .orElseThrow(() -> new IllegalArgumentException("Rating not found"));
    }

    public Page<Rating> getRatings(RatingSearchParameters searchParameters, Pageable pageable) {
        ratingValidator.validateSearchParameters(searchParameters);
        var createdFrom = searchParameters.createdFrom();
        var authorId = searchParameters.authorId();
        if (createdFrom == null) {
            createdFrom = clockProvider.getClock().instant().minus(360, ChronoUnit.DAYS);
        }
        return ratingRepository.findByCreatedDateAfterAndAuthorId(createdFrom, authorId, pageable)
                               .map(ratingMapper::toDomain);
    }
}
