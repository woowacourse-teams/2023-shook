package shook.shook.song.domain.killingpart.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shook.shook.member.domain.Member;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.dto.SongKillingPartKillingPartLikeCreatedAtDto;

@Repository
public interface KillingPartLikeRepository extends JpaRepository<KillingPartLike, Long> {

    Optional<KillingPartLike> findByKillingPartAndMember(final KillingPart killingPart,
                                                         final Member member);

    List<KillingPartLike> findAllByMemberAndIsDeleted(final Member member, final boolean isDeleted);

    @Query("SELECT s as song, kp as killingPart, kp_like.createdAt as killingPartLikeCreatedAt "
        + "FROM Song s "
        + "LEFT JOIN KillingPart kp ON s = kp.song "
        + "LEFT JOIN KillingPartLike kp_like ON kp = kp_like.killingPart "
        + "WHERE kp_like.member = :member and kp_like.isDeleted = false")
    List<SongKillingPartKillingPartLikeCreatedAtDto> findLikedKillingPartAndSongByMember(
        @Param("member") final Member member
    );

    @Query("SELECT kp_like.killingPart.id "
        + "FROM KillingPartLike kp_like "
        + "WHERE kp_like.member=:member and kp_like.isDeleted=false")
    List<Long> findLikedKillingPartIdsByMember(@Param("member") final Member member);

    @Query("update KillingPartLike kp_like set kp_like.isDeleted = false where kp_like.id = :id")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void pressLike(@Param("id") final Long killingPartLikeId);

    @Query("update KillingPartLike kp_like set kp_like.isDeleted = true where kp_like.id = :id")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void cancelLike(@Param("id") final Long killingPartLikeId);
}
