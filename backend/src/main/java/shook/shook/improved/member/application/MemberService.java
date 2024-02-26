package shook.shook.improved.member.application;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.improved.member.domain.Identifier;
import shook.shook.improved.member.domain.Member;
import shook.shook.improved.member.domain.Nickname;
import shook.shook.improved.member.domain.repository.MemberRepository;
import shook.shook.improved.member.exception.MemberException.ExistMemberException;
import shook.shook.improved.member.exception.MemberException.MemberNotExistException;


@RequiredArgsConstructor
@Service
public class MemberService {

    private static final String BASIC_NICKNAME = "shook";

    private final MemberRepository memberRepository;

    @Transactional
    public Member register(final String email) {
        findByIdentifier(email).ifPresent(member -> {
            throw new ExistMemberException(Map.of("Email", email));
        });

        final Member newMember = new Member(email, BASIC_NICKNAME);
        final Member savedMember = memberRepository.save(newMember);
        savedMember.updateNickname(savedMember.getNickname() + savedMember.getId());
        return savedMember;
    }

    public Optional<Member> findByIdentifier(final String identifier) {
        return memberRepository.findByIdentifier(new Identifier(identifier));
    }

    public Member findByIdAndNicknameThrowIfNotExist(final Long id, final Nickname nickname) {
        return memberRepository.findByIdAndNickname(id, nickname)
            .orElseThrow(
                () -> new MemberNotExistException(
                    Map.of("Id", String.valueOf(id), "Nickname", nickname.getValue())
                )
            );
    }
}
