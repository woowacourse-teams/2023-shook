package shook.shook.voting_song.domain;

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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.part.domain.PartLength;
import shook.shook.part.exception.PartException;
import shook.shook.voting_song.exception.VoteException;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@Table(name = "voting_song_part")
@Entity
public class VotingSongPart {

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
    @JoinColumn(name = "voting_song_id", foreignKey = @ForeignKey(name = "none"), updatable = false, nullable = false)
    @Getter(AccessLevel.NONE)
    private VotingSong votingSong;

    @OneToMany(mappedBy = "votingSongPart")
    private final List<Vote> votes = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    private VotingSongPart(
        final Long id,
        final int startSecond,
        final PartLength length,
        final VotingSong votingSong
    ) {
        this.id = id;
        this.startSecond = startSecond;
        this.length = length;
        this.votingSong = votingSong;
    }

    public static VotingSongPart saved(
        final Long id,
        final int startSecond,
        final PartLength length,
        final VotingSong votingSong
    ) {
        validateStartSecond(startSecond, length, votingSong.getLength());
        return new VotingSongPart(id, startSecond, length, votingSong);
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

    public static VotingSongPart forSave(
        final int startSecond,
        final PartLength length,
        final VotingSong votingSong
    ) {
        validateStartSecond(startSecond, length, votingSong.getLength());
        return new VotingSongPart(null, startSecond, length, votingSong);
    }

    public void vote(final Vote vote) {
        validateVote(vote);
        this.votes.add(vote);
    }

    private void validateVote(final Vote vote) {
        if (vote.isBelongToOtherPart(this)) {
            throw new VoteException.VoteForOtherPartException();
        }
        if (votes.contains(vote)) {
            throw new VoteException.DuplicateVoteExistException();
        }
    }

    public boolean hasEqualStartAndLength(final VotingSongPart other) {
        return this.startSecond == other.startSecond && this.length.equals(other.length);
    }

    public boolean isBelongToOtherSong(final VotingSong votingSong) {
        return !votingSong.equals(this.votingSong);
    }

    public int getEndSecond() {
        return length.getEndSecond(startSecond);
    }

    public List<Vote> getVotes() {
        return new ArrayList<>(votes);
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
        final VotingSongPart votingSongPart = (VotingSongPart) o;
        if (Objects.isNull(votingSongPart.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, votingSongPart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
