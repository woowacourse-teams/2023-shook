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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.part.domain.Part;
import shook.shook.part.exception.PartException;

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
    private Singer singer;

    @Embedded
    private SongLength length;

    @Embedded
    private Parts parts = new Parts();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Song(final String title, final String videoUrl, final String singer, final int length) {
        this.id = null;
        this.title = new SongTitle(title);
        this.videoUrl = new SongVideoUrl(videoUrl);
        this.singer = new Singer(singer);
        this.length = new SongLength(length);
    }

    public Optional<Part> getSameLengthPartStartAt(final Part other) {
        return parts.getSameLengthPartStartAt(other);
    }

    public void addPart(final Part part) {
        validatePart(part);
        parts.addPart(part);
    }

    private void validatePart(final Part part) {
        if (part.isBelongToOtherSong(this)) {
            throw new PartException.PartForOtherSongException();
        }
    }

    public boolean isUniquePart(final Part newPart) {
        return parts.isUniquePart(newPart);
    }

    public Optional<Part> getTopKillingPart() {
        return parts.getTopKillingPart();
    }

    public List<Part> getKillingParts() {
        return parts.getKillingParts();
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getVideoUrl() {
        return videoUrl.getValue();
    }

    public String getSinger() {
        return singer.getName();
    }

    public int getLength() {
        return length.getValue();
    }

    public List<Part> getParts() {
        return parts.getParts();
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
