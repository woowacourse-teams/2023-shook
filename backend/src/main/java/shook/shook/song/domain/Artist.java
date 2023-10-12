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
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "artist")
@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ProfileImageUrl profileImageUrl;

    @Embedded
    private ArtistName artistName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    public Artist(final ProfileImageUrl profileImageUrl, final ArtistName artistName) {
        this.profileImageUrl = profileImageUrl;
        this.artistName = artistName;
    }

    public boolean nameStartsWith(final String keyword) {
        return artistName.startsWithIgnoringCaseAndWhiteSpace(keyword);
    }

    public boolean nameEndsWith(final String keyword) {
        return artistName.endsWithIgnoringCaseAndWhiteSpace(keyword);
    }

    public String getArtistName() {
        return artistName.getValue();
    }

    public String getProfileImageUrl() {
        return profileImageUrl.getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Artist artist = (Artist) o;
        if (Objects.isNull(artist.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, artist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
