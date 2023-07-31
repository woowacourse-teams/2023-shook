package shook.shook.song.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.SongTitle;
import shook.shook.song.domain.repository.dto.SongTotalVoteCountDto;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<Song> findByTitle(final SongTitle title);

    @Query("SELECT s AS song, COUNT(v) AS totalVoteCount FROM Song s LEFT JOIN s.parts.parts p LEFT JOIN p.votes v GROUP BY s.id")
    List<SongTotalVoteCountDto> findSongWithTotalVoteCount();
}
