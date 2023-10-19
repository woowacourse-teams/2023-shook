package shook.shook.song.domain;

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
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.exception.killingpart.KillingPartsException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "song")
@Entity
public class Song {

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
    private KillingParts killingParts = new KillingParts();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id", foreignKey = @ForeignKey(name = "none"), updatable = false, nullable = false)
    private Artist artist;

    @Embedded
    private SongLength length;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;


    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    private Song(
        final Long id,
        final String title,
        final String videoId,
        final String imageUrl,
        final Artist artist,
        final int length,
        final Genre genre,
        final KillingParts killingParts
    ) {
        validate(killingParts);
        this.id = id;
        this.title = new SongTitle(title);
        this.videoId = new SongVideoId(videoId);
        this.albumCoverUrl = new AlbumCoverUrl(imageUrl);
        this.artist = artist;
        this.length = new SongLength(length);
        this.genre = genre;
        killingParts.setSong(this);
        this.killingParts = killingParts;
    }

    public Song(
        final String title,
        final String videoId,
        final String albumCoverUrl,
        final Artist artist,
        final int length,
        final Genre genre,
        final KillingParts killingParts
    ) {
        this(null, title, videoId, albumCoverUrl, artist, length, genre, killingParts);
    }

    private void validate(final KillingParts killingParts) {
        if (Objects.isNull(killingParts)) {
            throw new KillingPartsException.EmptyKillingPartsException();
        }
    }

    public boolean hasFullKillingParts() {
        return killingParts.isFull();
    }

    public String getPartVideoUrl(final KillingPart part) {
        return videoId.convertToVideoUrl() + part.getStartAndEndUrlPathParameter();
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

    public List<KillingPart> getKillingParts() {
        return killingParts.getKillingParts();
    }

    public List<KillingPart> getLikeCountSortedKillingParts() {
        return killingParts.getKillingPartsSortedByLikeCount();
    }

    public int getTotalLikeCount() {
        return killingParts.getKillingPartsTotalLikeCount();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Song song = (Song) o;
        if (Objects.isNull(song.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, song.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
