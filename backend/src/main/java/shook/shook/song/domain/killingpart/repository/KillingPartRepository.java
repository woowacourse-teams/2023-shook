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

    @Modifying
    @Query("UPDATE KillingPart kp SET kp.likeCount = kp.likeCount + 1 WHERE kp.id = :id")
    void increaseKillingPartLikeCount(@Param("id") final Long killingPartId);

    @Modifying
    @Query("UPDATE KillingPart kp SET kp.likeCount = kp.likeCount - 1 WHERE kp.id = :id")
    void decreaseKillingPartLikeCount(@Param("id") final Long killingPartId);
}
