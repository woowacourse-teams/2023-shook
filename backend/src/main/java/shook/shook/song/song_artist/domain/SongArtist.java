package shook.shook.song.song_artist.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "song_artist")
@Entity
public class SongArtist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "song_id")
    private Long songId;

    @Column(name = "artist_id")
    private Long artistId;

    private SongArtist(final Long id, final Long songId, final Long artistId) {
        this.id = id;
        this.songId = songId;
        this.artistId = artistId;
    }

    public SongArtist(final Long songId, final Long artistId) {
        this(null, songId, artistId);
    }
}
