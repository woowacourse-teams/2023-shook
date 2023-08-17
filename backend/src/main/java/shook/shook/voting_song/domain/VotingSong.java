package shook.shook.voting_song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.song.domain.AlbumCoverUrl;
import shook.shook.song.domain.Singer;
import shook.shook.song.domain.SongLength;
import shook.shook.song.domain.SongTitle;
import shook.shook.song.domain.SongVideoId;
import shook.shook.voting_song.exception.VotingSongPartException;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@Table(name = "voting_song")
@Entity
public class VotingSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private SongTitle title;

    @Embedded
    private SongVideoId videoId;

    @Embedded
    private AlbumCoverUrl albumCoverUrl;

    @Embedded
    private Singer singer;

    @Embedded
    private SongLength length;

    @Embedded
    private final VotingSongParts votingSongParts = new VotingSongParts();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public VotingSong(
        final String title,
        final String videoId,
        final String albumCoverUrl,
        final String singer,
        final int length
    ) {
        this.id = null;
        this.title = new SongTitle(title);
        this.videoId = new SongVideoId(videoId);
        this.albumCoverUrl = new AlbumCoverUrl(albumCoverUrl);
        this.singer = new Singer(singer);
        this.length = new SongLength(length);
    }

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    public Optional<VotingSongPart> getSameLengthPartStartAt(final VotingSongPart other) {
        return votingSongParts.getSameLengthPartStartAt(other);
    }

    public void addPart(final VotingSongPart votingSongPart) {
        validatePart(votingSongPart);
        votingSongParts.addPart(votingSongPart);
    }

    private void validatePart(final VotingSongPart votingSongPart) {
        if (votingSongPart.isBelongToOtherSong(this)) {
            throw new VotingSongPartException.PartForOtherSongException();
        }
    }

    public boolean isUniquePart(final VotingSongPart newVotingSongPart) {
        return votingSongParts.isUniquePart(newVotingSongPart);
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getVideoId() {
        return videoId.getValue();
    }

    public String getAlbumCoverUrl() {
        return albumCoverUrl.getValue();
    }

    public String getSinger() {
        return singer.getName();
    }

    public int getLength() {
        return length.getValue();
    }

    public List<VotingSongPart> getParts() {
        return votingSongParts.getVotingSongParts();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VotingSong votingSong = (VotingSong) o;
        if (Objects.isNull(votingSong.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, votingSong.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
