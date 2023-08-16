package shook.shook.song.domain.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.dto.SongTotalLikeCountDto;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("SELECT s AS song, SUM(COALESCE(kp.likeCount, 0)) AS totalLikeCount "
        + "FROM Song s LEFT JOIN s.killingParts.killingParts kp "
        + "GROUP BY s.id")
    List<SongTotalLikeCountDto> findAllWithTotalLikeCount();

    @Query("SELECT s FROM Song s "
        + "LEFT JOIN s.killingParts.killingParts kp "
        + "GROUP BY s.id "
        + "HAVING SUM(COALESCE(kp.likeCount, 0)) < (SELECT SUM(COALESCE(kp2.likeCount, 0)) FROM KillingPart kp2 WHERE kp2.song.id = :id) "
        + "OR (SUM(COALESCE(kp.likeCount, 0)) = (SELECT SUM(COALESCE(kp3.likeCount, 0)) FROM KillingPart kp3 WHERE kp3.song.id = :id) AND s.id < :id) "
        + "ORDER BY SUM(COALESCE(kp.likeCount, 0)) DESC, s.id DESC")
    List<Song> findSongsWithLessLikeCountThanSongWithId(
        @Param("id") final Long songId,
        final Pageable pageable
    );

    @Query("SELECT s FROM Song s "
        + "LEFT JOIN s.killingParts.killingParts kp "
        + "GROUP BY s.id "
        + "HAVING (SUM(COALESCE(kp.likeCount, 0)) > (SELECT SUM(COALESCE(kp2.likeCount, 0)) FROM KillingPart kp2 WHERE kp2.song.id = :id) "
        + "OR (SUM(COALESCE(kp.likeCount, 0)) = (SELECT SUM(COALESCE(kp3.likeCount, 0)) FROM KillingPart kp3 WHERE kp3.song.id = :id) AND s.id > :id)) "
        + "ORDER BY SUM(COALESCE(kp.likeCount, 0)) DESC, s.id DESC")
    List<Song> findSongsWithMoreLikeCountThanSongWithId(
        @Param("id") final Long songId,
        final Pageable pageable
    );
}
