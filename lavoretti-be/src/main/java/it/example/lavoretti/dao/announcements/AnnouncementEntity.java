package it.example.lavoretti.dao.announcements;

import it.example.lavoretti.dao.offers.OfferEntity;
import it.example.lavoretti.domain.announcements.CategoryAnnouncementType;
import it.example.lavoretti.domain.announcements.CreateAnnouncement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
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
@Entity(name = "Announcement")
@Table(name = "announcements")
@ParametersAreNonnullByDefault
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class AnnouncementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false, name = "author_id")
    private UUID authorId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String title;

    @Enumerated
    @Column(nullable = false, name = "category_type")
    private CategoryAnnouncementType categoryAnnouncement;

    @Column(nullable = false)
    private Money budget;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OfferEntity> offers = Set.of();

    @OneToOne
    @JoinColumn(name = "selected_offer_id")
    private OfferEntity selectedOffer;

    public AnnouncementEntity(UUID id,
                              String title,
                              String description,
                              CategoryAnnouncementType categoryAnnouncementType,
                              Money budget) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryAnnouncement = categoryAnnouncementType;
        this.budget = budget;
    }

    public AnnouncementEntity(CreateAnnouncement announcement, UUID authorId) {
        this.title = announcement.title();
        this.description = announcement.description();
        this.categoryAnnouncement = announcement.categoryAnnouncement();
        this.budget = announcement.budget();
        this.authorId = authorId;
    }
}
