package it.example.lavoretti.dao.offers;

import it.example.lavoretti.dao.announcements.AnnouncementEntity;
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
import org.javamoney.moneta.Money;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@ToString(callSuper = true)
@Setter
@Getter
@NoArgsConstructor
@Entity(name = "OfferEntity")
@Table(name = "offers")
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private Money amount;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "announcement_id")
    private AnnouncementEntity announcementEntity;
}
