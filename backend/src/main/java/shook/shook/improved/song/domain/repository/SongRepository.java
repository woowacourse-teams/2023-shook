package shook.shook.improved.song.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shook.shook.improved.song.domain.Genre;
import shook.shook.improved.song.domain.Song;
import shook.shook.improved.song.domain.repository.dto.SongArtists;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("SELECT s, a FROM Song s "
        + "JOIN SongArtist sa ON sa.songId = s.id "
        + "JOIN Artist a ON sa.artistId = a.id "
        + "GROUP BY s.id")
    List<SongArtists> findAllWithArtist();

    @Query("SELECT s, a FROM Song s "
        + "JOIN SongArtist sa ON sa.songId = s.id "
        + "JOIN Artist a ON sa.artistId = a.id "
        + "WHERE s.genre = :genre "
        + "GROUP BY s.id")
    List<SongArtists> findAllWithArtistByGenre(Genre genre);
}
