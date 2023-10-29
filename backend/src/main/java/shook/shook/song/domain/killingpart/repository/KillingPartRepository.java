package shook.shook.song.domain.killingpart.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;

@Repository
public interface KillingPartRepository extends JpaRepository<KillingPart, Long> {

    List<KillingPart> findAllBySong(final Song song);

    @Query("update KillingPart kp set kp.likeCount = kp.likeCount + 1 where kp.id = :id")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void increaseLikeCount(@Param("id") final Long killingPartLikeId);

    @Query("update KillingPart kp set kp.likeCount = kp.likeCount - 1 where kp.id = :id")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void decreaseLikeCount(@Param("id") final Long killingPartLikeId);
}
