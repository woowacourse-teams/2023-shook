package shook.shook.song.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.SongTitle;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<Song> findByTitle(final SongTitle title);
}
