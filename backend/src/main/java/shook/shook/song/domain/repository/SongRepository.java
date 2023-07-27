package shook.shook.song.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.SongTitle;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<Song> findByTitle(final SongTitle title);

    @Query("SELECT s FROM Song s WHERE LOWER(s.singer.name) = LOWER(?1)")
    List<Song> findAllBySingerIgnoringCase(final String singer);

    @Query("SELECT s FROM Song s WHERE LOWER(s.title.value) = LOWER(?1)")
    List<Song> findAllByTitleIgnoringCase(final String title);
}
