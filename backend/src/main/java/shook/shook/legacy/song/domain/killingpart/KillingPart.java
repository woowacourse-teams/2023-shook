package shook.shook.legacy.song.domain.killingpart;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.legacy.member.domain.Member;
import shook.shook.legacy.part.domain.PartLength;
import shook.shook.legacy.song.domain.Song;
import shook.shook.improved.song.exception.SongException.SongNotExistException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartCommentException.CommentForOtherPartException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartException.SongMaxKillingPartExceededException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartException.SongNotUpdatableException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartLikeException.EmptyLikeException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartLikeException.LikeForOtherKillingPartException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "killing_part")
@Entity
public class KillingPart {

    private static final int MINIMUM_START = 0;
    private static final String EMBED_LINK_PATH_PARAMETER_FORMAT = "?start=%d&end=%d";

    @Embedded
    private final KillingPartComments comments = new KillingPartComments();
    @Embedded
    private final KillingPartLikes killingPartLikes = new KillingPartLikes();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false)
    private int startSecond;
    @Embedded
    private PartLength length;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", foreignKey = @ForeignKey(name = "none"), updatable = false, nullable = false)
//    @Getter(AccessLevel.NONE) 좋아요한 킬링파트 탐색 성능을 위해 사용
    private Song song;
    @Convert(converter = LikeCountConverter.class)
    @Column(nullable = false)
    private AtomicInteger likeCount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    private KillingPart(
        final Long id,
        final int startSecond,
        final int length,
        final Song song,
        final int likeCount
    ) {
        this.id = id;
        this.startSecond = startSecond;
        this.length = new PartLength(length);
        this.song = song;
        this.likeCount = new AtomicInteger(likeCount);
    }

    private KillingPart(final int startSecond, final int length) {
        this(null, startSecond, length, null, 0);
    }

    public static KillingPart saved(
        final Long id,
        final int startSecond,
        final int length,
        final Song song
    ) {
        return new KillingPart(id, startSecond, length, song, 0);
    }

    public static KillingPart forSave(final int startSecond, final int length) {
        return new KillingPart(startSecond, length);
    }

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    public void addComment(final KillingPartComment comment) {
        if (comment.isBelongToOtherKillingPart(this)) {
            throw new CommentForOtherPartException(
                Map.of(
                    "CommentContent", comment.getContent(),
                    "KillingPartId", String.valueOf(this.id)
                )
            );
        }
        comments.addComment(comment);
    }

    public boolean like(final KillingPartLike likeToAdd) {
        validateLikeUpdate(likeToAdd);
        final boolean isLikeCreated = killingPartLikes.addLike(likeToAdd);
        if (isLikeCreated) {
            likeCount.incrementAndGet();
            return true;
        }
        return false;
    }

    private void validateLikeUpdate(final KillingPartLike like) {
        if (Objects.isNull(like)) {
            throw new EmptyLikeException();
        }
        if (like.isBelongToOtherKillingPart(this)) {
            throw new LikeForOtherKillingPartException(
                Map.of(
                    "KillingPartId", String.valueOf(this.id),
                    "KillingPartLikeId", String.valueOf(like.getId())
                )
            );
        }
    }

    public boolean unlike(final KillingPartLike likeToDelete) {
        validateLikeUpdate(likeToDelete);
        final boolean isLikeDeleted = killingPartLikes.deleteLike(likeToDelete);
        if (isLikeDeleted) {
            likeCount.decrementAndGet();
            return true;
        }
        return false;
    }

    public Optional<KillingPartLike> findLikeByMember(final Member member) {
        return killingPartLikes.getLikeByMember(member);
    }

    public boolean isLikedByMember(final Member member) {
        if (Objects.isNull(member)) {
            return false;
        }
        return findLikeByMember(member).isPresent();
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

    public List<KillingPartLike> getKillingPartLikes() {
        return new ArrayList<>(killingPartLikes.getLikes());
    }

    public int getLength() {
        return length.getValue();
    }

    public int getLikeCount() {
        return likeCount.get();
    }

    public void setSong(final Song song) {
        if (Objects.nonNull(this.song)) {
            throw new SongNotUpdatableException(
                Map.of(
                    "KillingPartId", String.valueOf(this.id),
                    "SongId", String.valueOf(song.getId())
                )
            );
        }
        if (Objects.isNull(song)) {
            throw new SongNotExistException();
        }
        if (song.hasFullKillingParts()) {
            throw new SongMaxKillingPartExceededException(
                Map.of(
                    "KillingPartId", String.valueOf(this.id),
                    "SongId", String.valueOf(song.getId())
                )
            );
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
