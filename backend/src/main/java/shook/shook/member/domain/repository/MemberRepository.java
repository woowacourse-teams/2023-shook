package shook.shook.member.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shook.shook.member.domain.Email;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.Nickname;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final Email email);

    Optional<Member> findByIdAndNickname(final Long id, final Nickname nickname);

    boolean existsMemberByNickname(final Nickname nickname);
}
