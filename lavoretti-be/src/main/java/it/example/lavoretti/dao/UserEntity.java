package it.example.lavoretti.dao;


import it.example.lavoretti.domain.users.RoleType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
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
@Entity(name = "User")
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Enumerated(value = jakarta.persistence.EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Column(nullable = false)
    private String password;

    @Column
    private String salt;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column
    private boolean accountExpired = false;

    @Column
    private boolean locked = false;

    @Column
    private boolean credentialsExpired = false;

    @Column
    private boolean enabled = true;

    @Column
    private Instant expiredAt;

    public UserEntity(@Nonnull String username,
                      @Nonnull String email,
                      @Nonnull String password,
                      @Nonnull RoleType role) {
        super();
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public UserEntity(@Nonnull String username,
                      @Nonnull String email,
                      @Nonnull String password,
                      @Nonnull RoleType role,
                      boolean isEnabled) {
        super();
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.enabled = isEnabled;
    }

    public UserEntity(@Nonnull String username,
                      @Nonnull String email,
                      @Nonnull String password,
                      @Nonnull String salt,
                      @Nonnull RoleType role) {
        super();
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.salt = salt;
    }
}

