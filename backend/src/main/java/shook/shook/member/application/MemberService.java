package shook.shook.member.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.member.domain.Email;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member register(final String email) {
        findByEmail(new Email(email))
            .ifPresent(member -> {
                throw new MemberException.ExistMemberException();
            });
        final Member newMember = new Member(email, email);
        return memberRepository.save(newMember);
    }

    public Optional<Member> findByEmail(final Email email) {
        return memberRepository.findByEmail(email);
    }

    public Member findById(final Long id) {
        return memberRepository.findById(id)
            .orElseThrow(MemberException.MemberNotExistException::new);
    }
}
