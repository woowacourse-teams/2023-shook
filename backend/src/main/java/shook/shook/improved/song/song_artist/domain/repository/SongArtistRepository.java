package shook.shook.improved.song.song_artist.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shook.shook.improved.song.song_artist.domain.SongArtist;

public interface SongArtistRepository extends JpaRepository<SongArtist, Long> {

    List<SongArtist> findBySongId(Long songId);
}
