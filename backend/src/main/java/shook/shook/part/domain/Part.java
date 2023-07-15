package shook.shook.part.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.part.exception.PartException;
import shook.shook.part.exception.VoteException;
import shook.shook.song.domain.Song;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "part")
@Entity
public class Part {

    private static final int MINIMUM_START = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private int startSecond;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PartLength length;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", foreignKey = @ForeignKey(name = "none"), updatable = false)
    @Getter(AccessLevel.NONE)
    private Song song;

    @OneToMany(mappedBy = "part")
    private final Set<Vote> votes = new HashSet<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
    }

    private Part(final Long id, final int startSecond, final PartLength length, final Song song) {
        this.id = id;
        this.startSecond = startSecond;
        this.length = length;
        this.song = song;
    }

    public static Part persisted(
        final Long id,
        final int startSecond,
        final PartLength length,
        final Song song
    ) {
        validateStartSecond(startSecond, length, song.getLength());
        return new Part(id, startSecond, length, song);
    }

    private static void validateStartSecond(
        final int startSecond,
        final PartLength partLength,
        final int songLength
    ) {
        if (startSecond < MINIMUM_START) {
            throw new PartException.StartLessThanZeroException();
        }
        if (songLength <= startSecond) {
            throw new PartException.StartOverSongLengthException();
        }
        if (songLength < partLength.getEndSecond(startSecond)) {
            throw new PartException.EndOverSongLengthException();
        }
    }

    public static Part notPersisted(
        final int startSecond,
        final PartLength length,
        final Song song
    ) {
        validateStartSecond(startSecond, length, song.getLength());
        return new Part(null, startSecond, length, song);
    }

    public void vote(final Vote vote) {
        validateVote(vote);
        this.votes.add(vote);
    }

    private void validateVote(final Vote vote) {
        if (vote.isBelongToOtherPart((this))) {
            throw new VoteException.VoteForOtherPartException();
        }
    }

    public boolean hasEqualStartAndLength(final Part other) {
        return this.startSecond == other.startSecond && this.length.equals(other.length);
    }

    public boolean isBelongTo(final Song song) {
        return song.equals(this.song);
    }

    public Set<Vote> getVotes() {
        return new HashSet<>(votes);
    }

    public int getVoteCount() {
        return votes.size();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Part part = (Part) o;
        if (Objects.isNull(part.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, part.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
