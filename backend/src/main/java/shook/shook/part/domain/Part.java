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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private static final String EMBED_LINK_PATH_PARAMETER_FORMAT = "?start=%d&end=%d";

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
    private final List<Vote> votes = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    private Part(final Long id, final int startSecond, final PartLength length, final Song song) {
        this.id = id;
        this.startSecond = startSecond;
        this.length = length;
        this.song = song;
    }

    public static Part saved(
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

    public static Part forSave(
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
        if (vote.isBelongToOtherPart(this)) {
            throw new VoteException.VoteForOtherPartException();
        }
        if (votes.contains(vote)) {
            throw new VoteException.DuplicateVoteExistException();
        }
    }

    public boolean hasEqualStartAndLength(final Part other) {
        return this.startSecond == other.startSecond && this.length.equals(other.length);
    }

    public boolean isBelongToOtherSong(final Song song) {
        return !song.equals(this.song);
    }

    //TODO: 반환하는 형태가 변할 가능성 있어 리팩토링 대상
    public String getStartAndEndUrlPathParameter() {
        return String.format(EMBED_LINK_PATH_PARAMETER_FORMAT, startSecond, getEndSecond());
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
