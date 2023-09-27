package shook.shook.member.application;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.auth.application.TokenProvider;
import shook.shook.auth.application.dto.TokenPair;
import shook.shook.auth.exception.AuthorizationException;
import shook.shook.auth.repository.InMemoryTokenPairRepository;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.application.dto.NicknameUpdateRequest;
import shook.shook.member.domain.Email;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.Nickname;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.KillingPartCommentRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private static final String BASIC_NICKNAME = "shook";

    private final MemberRepository memberRepository;
    private final KillingPartCommentRepository commentRepository;
    private final KillingPartLikeRepository likeRepository;
    private final TokenProvider tokenProvider;
    private final InMemoryTokenPairRepository inMemoryTokenPairRepository;

    @Transactional
    public Member register(final String email) {
        findByEmail(email).ifPresent(member -> {
            throw new MemberException.ExistMemberException(Map.of("Email", email));
        });

        final Member newMember = new Member(email, BASIC_NICKNAME);
        final Member savedMember = memberRepository.save(newMember);
        savedMember.updateNickname(savedMember.getNickname() + savedMember.getId());

        return savedMember;
    }

    public Optional<Member> findByEmail(final String email) {
        return memberRepository.findByEmail(new Email(email));
    }

    public Member findByIdAndNicknameThrowIfNotExist(final Long id, final Nickname nickname) {
        return memberRepository.findByIdAndNickname(id, nickname)
            .orElseThrow(
                () -> new MemberException.MemberNotExistException(
                    Map.of("Id", String.valueOf(id), "Nickname", nickname.getValue())
                )
            );
    }

    @Transactional
    public void deleteById(final Long id, final MemberInfo memberInfo) {
        final Member member = getMemberIfValidRequest(id, memberInfo);

        final List<KillingPartLike> membersExistLikes = likeRepository.findAllByMemberAndIsDeleted(member, false);

        membersExistLikes.forEach(KillingPartLike::updateDeletion);
        commentRepository.deleteAllByMember(member);
        memberRepository.delete(member);
    }

    private Member getMemberIfValidRequest(final Long memberId, final MemberInfo memberInfo) {
        final long requestMemberId = memberInfo.getMemberId();
        final Member requestMember = findById(requestMemberId);
        final Member targetMember = findById(memberId);
        validateMemberAuthentication(requestMember, targetMember);

        return targetMember;
    }

    private Member findById(final Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new MemberException.MemberNotExistException(
                Map.of("Id", String.valueOf(id))
            ));
    }

    private void validateMemberAuthentication(final Member requestMember, final Member targetMember) {
        if (!requestMember.equals(targetMember)) {
            throw new AuthorizationException.UnauthenticatedException(
                Map.of(
                    "tokenMemberId", String.valueOf(requestMember.getId()),
                    "pathMemberId", String.valueOf(targetMember.getId())
                )
            );
        }
    }

    @Transactional
    public TokenPair updateNickname(final Long memberId, final MemberInfo memberInfo,
                                    final NicknameUpdateRequest request) {
        final Member member = getMemberIfValidRequest(memberId, memberInfo);
        final Nickname nickname = new Nickname(request.getNickname());

        if (isSameNickname(member, nickname)) {
            return null;
        }

        validateDuplicateNickname(nickname);
        member.updateNickname(nickname.getValue());
        memberRepository.save(member);

        return reissueTokenPair(member.getId(), member.getNickname());
    }

    private TokenPair reissueTokenPair(final Long memberId, final String nickname) {
        final String reissuedAccessToken = tokenProvider.createAccessToken(memberId, nickname);
        final String reissuedRefreshToken = tokenProvider.createRefreshToken(memberId, nickname);
        inMemoryTokenPairRepository.addOrUpdateTokenPair(reissuedRefreshToken, reissuedAccessToken);

        return new TokenPair(reissuedAccessToken, reissuedRefreshToken);
    }


    private boolean isSameNickname(final Member member, final Nickname nickname) {
        final String originalNickname = member.getNickname();
        final String nicknameForUpdate = nickname.getValue();

        return originalNickname.equals(nicknameForUpdate);
    }

    private void validateDuplicateNickname(final Nickname nickname) {
        final boolean isDuplicated = memberRepository.existsMemberByNickname(nickname);
        if (isDuplicated) {
            throw new MemberException.ExistNicknameException(
                Map.of("Nickname", nickname.getValue())
            );
        }
    }
}
