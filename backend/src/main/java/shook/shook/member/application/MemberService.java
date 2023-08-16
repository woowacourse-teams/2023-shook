package shook.shook.member.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.member.domain.Email;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.Nickname;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private static final String EMAIL_SPILT_DELIMITER = "@";
    private static final int NICKNAME_INDEX = 0;

    private final MemberRepository memberRepository;

    @Transactional
    public Member register(final String email) {
        findByEmail(new Email(email))
            .ifPresent(member -> {
                throw new MemberException.ExistMemberException();
            });
        final String nickname = email.split(EMAIL_SPILT_DELIMITER)[NICKNAME_INDEX];
        final Member newMember = new Member(email, nickname);
        return memberRepository.save(newMember);
    }

    public Optional<Member> findByEmail(final Email email) {
        return memberRepository.findByEmail(email);
    }

    public Member findByIdAndNickname(final Long id, final Nickname nickname) {
        return memberRepository.findByIdAndNickname(id, nickname)
            .orElseThrow(MemberException.MemberNotExistException::new);
    }

    public Member findById(final Long id) {
        return memberRepository.findById(id)
            .orElseThrow(MemberException.MemberNotExistException::new);
    }
}
