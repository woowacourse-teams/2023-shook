package shook.shook.song.domain.killingpart.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shook.shook.member.domain.Member;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;

@Repository
public interface KillingPartLikeRepository extends JpaRepository<KillingPartLike, Long> {

    Optional<KillingPartLike> findByKillingPartAndMember(final KillingPart killingPart,
        final Member member);

    List<KillingPartLike> findAllByMemberAndIsDeleted(final Member member, final boolean isDeleted);

    @Query("SELECT kpl.killingPart.id FROM KillingPartLike kpl WHERE kpl.member = :member")
    List<Long> findKillingPartIdsByMember(final @Param("member") Member member);
}
