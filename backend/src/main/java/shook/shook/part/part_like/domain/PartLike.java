package shook.shook.part.part_like.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "killing_part_like")
@Entity
public class PartLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "part_id", nullable = false, updatable = false)
    private Long partId;

    @Column(name = "member_id", nullable = false, updatable = false)
    private Long memberId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    private PartLike(final Long id, final boolean isDeleted, final Long partId, final Long memberId) {
        this.id = id;
        this.isDeleted = isDeleted;
        this.partId = partId;
        this.memberId = memberId;
    }

    public PartLike(final Long partId, final Long memberId) {
        this(null, true, partId, memberId);
    }

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    public void updateDeletion() {
        this.isDeleted = !this.isDeleted;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PartLike partLike = (PartLike) o;
        if (Objects.isNull(partLike.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, partLike.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
