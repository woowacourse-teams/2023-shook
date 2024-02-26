package shook.shook.improved.part.member_part.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_part")
@Entity
public class MemberPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "part_id", nullable = false, updatable = false)
    private Long partId;

    @Column(name = "song_id", nullable = false, updatable = false)
    private Long songId;

    @Column(name = "member_id", nullable = false, updatable = false)
    private Long memberId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    private MemberPart(final Long id, final Long partId, final Long songId, final Long memberId) {
        this.id = id;
        this.partId = partId;
        this.songId = songId;
        this.memberId = memberId;
    }

    public MemberPart(final Long partId, final Long songId, final Long memberId) {
        this(null, partId, songId, memberId);
    }

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MemberPart memberPart = (MemberPart) o;
        if (Objects.isNull(memberPart.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, memberPart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
