package it.example.lavoretti.dao.rating;

import java.time.Instant;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@ParametersAreNonnullByDefault
public interface RatingRepository extends JpaRepository<RatingEntity, UUID> {

    Page<RatingEntity> findByCreatedDateAfterAndAuthorId(Instant createdFrom, UUID authorId, Pageable pageable);
}
