package shook.shook.member.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shook.shook.member.application.dto.MemberRegisterRequest;
import shook.shook.member.domain.Email;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member register(final MemberRegisterRequest memberRegisterRequest) {
        final Optional<Member> member = findByEmail(new Email(memberRegisterRequest.getEmail()));

        if (member.isPresent()) {
            throw new MemberException.AlreadyExistMemberException();
        }
        final Member newMember = memberRegisterRequest.toMember();
        return memberRepository.save(newMember);
    }

    public Optional<Member> findByEmail(final Email email) {
        return memberRepository.findByEmail(email);
    }
}
