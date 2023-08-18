package shook.shook.song.domain.killingpart;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.member.domain.Member;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "killing_part_like")
@Entity
public class KillingPartLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "killing_part_id", foreignKey = @ForeignKey(name = "none"), updatable = false, nullable = false)
//    @Getter(AccessLevel.NONE) 좋아요한 킬링파트 탐색 성능을 위해 사용
    private KillingPart killingPart;

    @ManyToOne
    @Getter(AccessLevel.NONE)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "none"), updatable = false, nullable = false)
    private Member member;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    public KillingPartLike(final KillingPart killingPart, final Member member) {
        this.killingPart = killingPart;
        this.member = member;
        this.isDeleted = true;
    }

    public void updateDeletion() {
        this.isDeleted = !this.isDeleted;
    }

    public boolean isOwner(final Member member) {
        return this.member.equals(member);
    }

    public boolean isBelongToOtherKillingPart(final KillingPart killingPart) {
        return !this.killingPart.equals(killingPart);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KillingPartLike killingPartLike = (KillingPartLike) o;
        if (Objects.isNull(killingPartLike.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, killingPartLike.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
