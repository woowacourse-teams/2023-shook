package shook.shook.song.domain.killingpart;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.Song;
import shook.shook.song.exception.SongException;
import shook.shook.song.exception.killingpart.KillingPartCommentException;
import shook.shook.song.exception.killingpart.KillingPartException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "killing_part")
@Entity
public class KillingPart {

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
    @JoinColumn(name = "song_id", foreignKey = @ForeignKey(name = "none"), updatable = false, nullable = false)
    @Getter(AccessLevel.NONE)
    private Song song;

    @Embedded
    private final KillingPartComments comments = new KillingPartComments();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    private KillingPart(
        final Long id,
        final int startSecond,
        final PartLength length,
        final Song song
    ) {
        this.id = id;
        this.startSecond = startSecond;
        this.length = length;
        this.song = song;
    }

    private KillingPart(final int startSecond, final PartLength length) {
        this(null, startSecond, length, null);
    }

    public static KillingPart saved(
        final Long id,
        final int startSecond,
        final PartLength length,
        final Song song
    ) {
        return new KillingPart(id, startSecond, length, song);
    }

    public static KillingPart forSave(final int startSecond, final PartLength length
    ) {
        return new KillingPart(startSecond, length);
    }

    public void addComment(final KillingPartComment comment) {
        if (comment.isBelongToOtherKillingPart(this)) {
            throw new KillingPartCommentException.CommentForOtherPartException();
        }
        comments.addComment(comment);
    }

    public String getStartAndEndUrlPathParameter() {
        return String.format(EMBED_LINK_PATH_PARAMETER_FORMAT, startSecond, getEndSecond());
    }

    public int getEndSecond() {
        return length.getEndSecond(startSecond);
    }

    public List<KillingPartComment> getComments() {
        return comments.getComments();
    }

    public List<KillingPartComment> getCommentsInRecentOrder() {
        return comments.getCommentsInRecentOrder();
    }

    public void setSong(final Song song) {
        if (Objects.nonNull(this.song)) {
            throw new KillingPartException.SongNotUpdatableException();
        }
        if (Objects.isNull(song)) {
            throw new SongException.SongNotExistException();
        }
        if (song.hasFullKillingParts()) {
            throw new KillingPartException.SongMaxKillingPartExceededException();
        }
        this.song = song;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KillingPart part = (KillingPart) o;
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
