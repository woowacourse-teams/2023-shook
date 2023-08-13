package shook.shook.song.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.dto.SongTotalLikeCountDto;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("SELECT s AS song, SUM(COALESCE(kp.likeCount, 0)) AS totalLikeCount "
        + "FROM Song s LEFT JOIN s.killingParts.killingParts kp "
        + "GROUP BY s.id")
    List<SongTotalLikeCountDto> findAllWithTotalLikeCount();
}
