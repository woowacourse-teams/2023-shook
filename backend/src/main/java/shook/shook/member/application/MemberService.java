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
        //(매우 사소)한줄에 1개의 점 정도는 괜찮을 것 같아요~
        findByEmail(email).ifPresent(member -> {
            throw new MemberException.ExistMemberException();
        });
        final String nickname = email.split(EMAIL_SPILT_DELIMITER)[NICKNAME_INDEX];
        final Member newMember = new Member(email, nickname);
        return memberRepository.save(newMember);
    }

    //파라미터 타입에 대한 리뷰 참고해주세요
    public Optional<Member> findByEmail(final String email) {
        return memberRepository.findByEmail(new Email(email));
    }

    //해당 메서드는 다른곳에서 코드 반복을 줄이기 위해서 사용되는 것으로 보여요~
    //내부적으로 없을 경우 예오로 처리한다는 내용이 메서드에 있어야 의도치 못한 예외 발생을 막을 수 있을 것 같아요
    public Member findByIdAndNicknameThrowIfNotExist(final Long id, final Nickname nickname) {
        return memberRepository.findByIdAndNickname(id, nickname)
            .orElseThrow(MemberException.MemberNotExistException::new);
    }
}
