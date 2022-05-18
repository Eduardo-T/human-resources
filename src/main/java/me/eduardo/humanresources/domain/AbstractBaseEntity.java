package me.eduardo.humanresources.domain;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractBaseEntity<ID> implements BaseEntity, IDPersistable<ID>, Serializable {

    @Column(nullable = false)
    private boolean isActive;
    @Column(nullable = false)
    private Date dateCreated;

    public AbstractBaseEntity(@NonNull boolean isActive, @NonNull Date dateCreated) {
        this.isActive = isActive;
        this.dateCreated = dateCreated;
    }

    public AbstractBaseEntity() {
        isActive = true;
        dateCreated = Date.from(Instant.now());
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    public void setActive(@NonNull boolean active) {
        isActive = active;
    }

    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(@NonNull Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
