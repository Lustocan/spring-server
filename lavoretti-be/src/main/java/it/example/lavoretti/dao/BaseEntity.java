package it.example.lavoretti.dao;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.Instant;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate
    @Column(
        name = "created_at"
    )
    protected Instant createdAt;

    @LastModifiedDate
    @Column(
        name = "updated_at"
    )
    protected Instant lastModified;

    @Version
    protected Long version;
}
