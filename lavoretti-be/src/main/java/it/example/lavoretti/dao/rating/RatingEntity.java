package it.example.lavoretti.dao.rating;

import it.example.lavoretti.dao.BaseEntity;
import it.example.lavoretti.dao.users.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@ToString(callSuper = true)
@Setter
@Getter
@NoArgsConstructor
@Entity(name = "RatingEntity")
@Table(name = "ratings")
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class RatingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private int rating;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "rated_by_user_id")
    private UserEntity ratedByUser;

    @ManyToOne
    @JoinColumn(name = "rated_user_id")
    private UserEntity ratedUser;

    public RatingEntity(int rating,
                        String comment,
                        UserEntity ratedByUser,
                        UserEntity ratedUser) {
        this.rating = rating;
        this.comment = comment;
        this.ratedByUser = ratedByUser;
        this.ratedUser = ratedUser;
    }
}
