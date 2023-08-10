package shook.shook.song.domain.voting_song;

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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "register")  // TODO: 2023/08/09 vote 로 이름 변경
@Entity
public class Register { // TODO: 2023/08/10 Vote 로 이름 변경

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voting_song_part_id", foreignKey = @ForeignKey(name = "none"), updatable = false)
    private VotingSongPart votingSongPart;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private Register(final Long id, final VotingSongPart votingSongPart) {
        this.id = id;
        this.votingSongPart = votingSongPart;
    }

    public static Register saved(final Long id, final VotingSongPart votingSongPart) {
        return new Register(id, votingSongPart);
    }

    public static Register forSave(final VotingSongPart votingSongPart) {
        return new Register(null, votingSongPart);
    }

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
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
        final Register register = (Register) o;
        if (Objects.isNull(register.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, register.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
