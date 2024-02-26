package shook.shook.improved.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private SongLength length;

    @Column(name = "genre")
    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @Column(name = "score", nullable = false)
    private int score = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    private Song(
        final Long id,
        final String title,
        final String videoId,
        final String imageUrl,
        final int length,
        final Genre genre,
        final int score
    ) {
        this.id = id;
        this.title = new SongTitle(title);
        this.videoId = new SongVideoId(videoId);
        this.albumCoverUrl = new AlbumCoverUrl(imageUrl);
        this.length = new SongLength(length);
        this.genre = genre;
        this.score = score;
    }

    public Song(
        final String title,
        final String videoId,
        final String albumCoverUrl,
        final int length,
        final String genre
    ) {
        this(null, title, videoId, albumCoverUrl, length, Genre.from(genre), 0);
    }

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
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

    public int getLength() {
        return length.getValue();
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
