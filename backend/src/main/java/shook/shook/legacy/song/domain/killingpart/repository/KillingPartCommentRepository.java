package shook.shook.legacy.song.domain.killingpart.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.legacy.member.domain.Member;
import shook.shook.legacy.song.domain.killingpart.KillingPartComment;

@Repository
public interface KillingPartCommentRepository extends JpaRepository<KillingPartComment, Long> {

    void deleteAllByMember(final Member member);

    List<KillingPartComment> findAllByMember(final Member member);
}
