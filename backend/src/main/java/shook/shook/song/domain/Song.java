package shook.shook.song.domain;

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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.song.domain.killingpart.KillingPart;

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
    private SongVideoUrl videoUrl;

    @Embedded
    private AlbumCoverUrl albumCoverUrl;

    @Embedded
    private Singer singer;

    @Embedded
    private SongLength length;

    @Embedded
    private KillingParts killingParts = new KillingParts();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public Song(
        final String title,
        final String videoUrl,
        final String imageUrl,
        final String singer,
        final int length
    ) {
        this.id = null;
        this.title = new SongTitle(title);
        this.videoUrl = new SongVideoUrl(videoUrl);
        this.albumCoverUrl = new AlbumCoverUrl(imageUrl);
        this.singer = new Singer(singer);
        this.length = new SongLength(length);
    }

    public Song(
        final String title,
        final String videoUrl,
        final String albumCoverUrl,
        final String singer,
        final int length,
        final KillingParts killingParts
    ) {
        this.title = new SongTitle(title);
        this.videoUrl = new SongVideoUrl(videoUrl);
        this.albumCoverUrl = new AlbumCoverUrl(albumCoverUrl);
        this.singer = new Singer(singer);
        this.length = new SongLength(length);
        this.killingParts = killingParts;
    }

    public String getPartVideoUrl(final KillingPart part) {
        return videoUrl.getValue() + part.getStartAndEndUrlPathParameter();
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getVideoUrl() {
        return videoUrl.getValue();
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

    public List<KillingPart> getKillingParts() {
        return killingParts.getKillingParts();
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
