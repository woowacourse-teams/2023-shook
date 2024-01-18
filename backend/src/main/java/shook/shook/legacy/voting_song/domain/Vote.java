package shook.shook.legacy.voting_song.domain;

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
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.legacy.member.domain.Member;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "vote")
@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voting_song_part_id", foreignKey = @ForeignKey(name = "none"), updatable = false, nullable = false)
    private VotingSongPart votingSongPart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "none"), updatable = false, nullable = false)
    private Member member;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    private Vote(final Long id, final Member member, final VotingSongPart votingSongPart) {
        this.id = id;
        this.member = member;
        this.votingSongPart = votingSongPart;
    }

    public static Vote saved(final Long id, final Member member, final VotingSongPart votingSongPart) {
        return new Vote(id, member, votingSongPart);
    }

    public static Vote forSave(final Member member, final VotingSongPart votingSongPart) {
        return new Vote(null, member, votingSongPart);
    }

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    public boolean isBelongToOtherPart(final VotingSongPart votingSongPart) {
        return !votingSongPart.equals(this.votingSongPart);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Vote vote = (Vote) o;
        if (Objects.isNull(vote.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, vote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
