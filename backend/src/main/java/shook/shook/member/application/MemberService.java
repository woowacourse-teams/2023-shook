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
        findByEmail(email).ifPresent(member -> {
            throw new MemberException.ExistMemberException();
        });
        final String nickname = email.split(EMAIL_SPILT_DELIMITER)[NICKNAME_INDEX];
        final Member newMember = new Member(email, nickname);
        return memberRepository.save(newMember);
    }

    public Optional<Member> findByEmail(final String email) {
        return memberRepository.findByEmail(new Email(email));
    }
    
    public Member findByIdAndNicknameThrowIfNotExist(final Long id, final Nickname nickname) {
        return memberRepository.findByIdAndNickname(id, nickname)
            .orElseThrow(MemberException.MemberNotExistException::new);
    }
}
