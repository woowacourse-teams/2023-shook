package shook.shook.song.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "artist_synonym")
@Entity
public class ArtistSynonym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id", foreignKey = @ForeignKey(name = "none"), updatable = false, nullable = false)
    private Artist artist;

    @Embedded
    private Synonym synonym;

    public ArtistSynonym(final Artist artist, final Synonym synonym) {
        this.artist = artist;
        this.synonym = synonym;
    }

    public boolean startsWith(final String keyword) {
        return synonym.startsWithIgnoringCaseAndWhiteSpace(keyword);
    }

    public boolean endsWith(final String keyword) {
        return synonym.endsWithIgnoringCaseAndWhiteSpace(keyword);
    }

    public String getArtistName() {
        return artist.getArtistName();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ArtistSynonym artistSynonym = (ArtistSynonym) o;
        if (Objects.isNull(artistSynonym.id) || Objects.isNull(this.id)) {
            return false;
        }
        return Objects.equals(id, artistSynonym.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
