package it.example.lavoretti.controller;

import it.example.lavoretti.domain.rating.CreateRating;
import it.example.lavoretti.domain.rating.Rating;
import it.example.lavoretti.service.rating.RatingSearchParameters;
import it.example.lavoretti.service.rating.RatingService;
import java.time.Instant;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
@ParametersAreNonnullByDefault
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody CreateRating createRating) {
        Rating createdRating = ratingService.createRating(createRating);
        return ResponseEntity.ok(createdRating);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRating(@PathVariable("id") UUID id) {
        Rating createdRating = ratingService.getById(id);
        return ResponseEntity.ok(createdRating);
    }

    @GetMapping
    public ResponseEntity<Page<Rating>> getRatings(@RequestParam("created_from") @Nullable Instant createdFrom,
                                                   @RequestParam("author_id") UUID authorId,
                                                   @PageableDefault Pageable pageable) {
        RatingSearchParameters searchParameters = new RatingSearchParameters(createdFrom, authorId);
        Page<Rating> ratings = ratingService.getRatings(searchParameters, pageable);
        return ResponseEntity.ok(ratings);
    }
}

