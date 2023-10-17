package shook.shook.song.domain.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.Artist;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.SongTitle;
import shook.shook.song.domain.repository.dto.SongTotalLikeCountDto;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("SELECT s AS song, SUM(COALESCE(kp.likeCount, 0)) AS totalLikeCount "
        + "FROM Song s LEFT JOIN s.killingParts.killingParts kp "
        + "GROUP BY s.id")
    List<SongTotalLikeCountDto> findAllWithTotalLikeCount();

    @Query("SELECT s AS song "
        + "FROM Song s "
        + "LEFT JOIN FETCH s.killingParts.killingParts kp "
        + "GROUP BY s.id, kp.id")
    List<Song> findAllWithKillingParts();

    @Query("SELECT s FROM Song s "
        + "LEFT JOIN s.killingParts.killingParts kp "
        + "GROUP BY s.id "
        + "HAVING SUM(COALESCE(kp.likeCount, 0)) < (SELECT SUM(COALESCE(kp2.likeCount, 0)) FROM KillingPart kp2 WHERE kp2.song.id = :id) "
        + "OR (SUM(COALESCE(kp.likeCount, 0)) = (SELECT SUM(COALESCE(kp3.likeCount, 0)) FROM KillingPart kp3 WHERE kp3.song.id = :id) AND s.id < :id) "
        + "ORDER BY SUM(COALESCE(kp.likeCount, 0)) DESC, s.id DESC")
        // id 로 song 찾아온느 쿼리 1개 -> 비즈니스 로직에서 조건 필터링 => 2번을 1번으로
        // 100개 이하의 데이터는 비즈니스 로직에서 정렬하는 것을 추천한다.
        // 조건이 확실하게 있는 경우는 쿼리에서 하는 것이 좋다.
    List<Song> findSongsWithLessLikeCountThanSongWithId(
        @Param("id") final Long songId,
        final Pageable pageable
    );

    @Query("SELECT s FROM Song s "
        + "LEFT JOIN s.killingParts.killingParts kp "
        + "GROUP BY s.id "
        + "HAVING (SUM(COALESCE(kp.likeCount, 0)) > (SELECT SUM(COALESCE(kp2.likeCount, 0)) FROM KillingPart kp2 WHERE kp2.song.id = :id) "
        + "OR (SUM(COALESCE(kp.likeCount, 0)) = (SELECT SUM(COALESCE(kp3.likeCount, 0)) FROM KillingPart kp3 WHERE kp3.song.id = :id) AND s.id > :id)) "
        + "ORDER BY SUM(COALESCE(kp.likeCount, 0)), s.id")
    List<Song> findSongsWithMoreLikeCountThanSongWithId(
        @Param("id") final Long songId,
        final Pageable pageable
    );

    boolean existsSongByTitle(final SongTitle title);

    @Query("SELECT s AS song, SUM(COALESCE(kp.likeCount, 0)) AS totalLikeCount "
        + "FROM Song s LEFT JOIN s.killingParts.killingParts kp "
        + "WHERE s.artist = :artist "
        + "GROUP BY s.id")
    List<SongTotalLikeCountDto> findAllSongsWithTotalLikeCountByArtist(
        @Param("artist") final Artist artist
    );
}
