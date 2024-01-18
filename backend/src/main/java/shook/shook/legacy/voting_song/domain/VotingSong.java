package shook.shook.legacy.voting_song.domain;

import jakarta.persistence.Column;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.legacy.song.domain.AlbumCoverUrl;
import shook.shook.legacy.song.domain.Artist;
import shook.shook.legacy.song.domain.SongLength;
import shook.shook.legacy.song.domain.SongTitle;
import shook.shook.legacy.song.domain.SongVideoId;
import shook.shook.legacy.voting_song.exception.VotingSongPartException.PartForOtherSongException;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@Table(name = "voting_song")
@Entity
public class VotingSong {

    @Embedded
    private final VotingSongParts votingSongParts = new VotingSongParts();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private SongTitle title;
    @Embedded
    private SongVideoId videoId;
    @Embedded
    private AlbumCoverUrl albumCoverUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", foreignKey = @ForeignKey(name = "none"), updatable = false, nullable = false)
    private Artist artist;
    @Embedded
    private SongLength length;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public VotingSong(
        final String title,
        final String videoId,
        final String albumCoverUrl,
        final Artist artist,
        final int length
    ) {
        this.id = null;
        this.title = new SongTitle(title);
        this.videoId = new SongVideoId(videoId);
        this.albumCoverUrl = new AlbumCoverUrl(albumCoverUrl);
        this.artist = artist;
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
            throw new PartForOtherSongException(
                Map.of(
                    "VotingSongPartId", String.valueOf(votingSongPart.getId()),
                    "VotingSongId", String.valueOf(this.id)
                )
            );
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

    public String getArtistName() {
        return artist.getArtistName();
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
