package shook.shook.improved.member.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shook.shook.improved.member.domain.Identifier;
import shook.shook.improved.member.domain.Member;
import shook.shook.improved.member.domain.Nickname;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdentifier(final Identifier identifier);

    Optional<Member> findByIdAndNickname(final Long id, final Nickname nickname);
}
