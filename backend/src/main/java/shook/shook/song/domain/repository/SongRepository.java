package shook.shook.song.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.SongTitle;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<Song> findByTitle(final SongTitle title);

    @Query("SELECT s FROM Song s WHERE LOWER(s.singer.name) = LOWER(:singer)")
    List<Song> findAllBySingerIgnoringCase(@Param("singer") final String singer);

    @Query("SELECT s FROM Song s WHERE LOWER(s.title.value) = LOWER(:title)")
    List<Song> findAllByTitleIgnoringCase(@Param("title") final String title);

    @Query("SELECT s FROM Song s WHERE LOWER(s.title.value) = LOWER(:title) AND LOWER(s.singer.name) = LOWER(:singer)")
    List<Song> findAllByTitleAndSingerIgnoringCase(
        @Param("title") final String title,
        @Param("singer") final String singer
    );
}
