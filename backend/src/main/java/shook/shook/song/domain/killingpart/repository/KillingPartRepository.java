package shook.shook.song.domain.killingpart.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;

@Repository
public interface KillingPartRepository extends JpaRepository<KillingPart, Long> {

    List<KillingPart> findAllBySong(final Song song);

    /* AtomicInteger 사용하면서 예외 발생하여 주석 처리
    @Query("update KillingPart kp set kp.likeCount = kp.likeCount + 1 where kp.id = :id")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void increaseLikeCount(@Param("id") final Long killingPartLikeId);

    @Query("update KillingPart kp set kp.likeCount = kp.likeCount - 1 where kp.id = :id")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void decreaseLikeCount(@Param("id") final Long killingPartLikeId);
     */
}
