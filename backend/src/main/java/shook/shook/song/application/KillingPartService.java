package shook.shook.song.application;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;
import shook.shook.member.exception.MemberException.MemberNotExistException;
import shook.shook.song.application.dto.LikedKillingPartResponse;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class KillingPartService {

    private final KillingPartLikeRepository killingPartLikeRepository;
    private final MemberRepository memberRepository;

    public List<LikedKillingPartResponse> findLikedKillingPartByMemberId(
        final MemberInfo memberInfo
    ) {
        if (memberInfo.getAuthority().isAnonymous()) {
            throw new MemberException.MemberNotExistException();
        }

        final Member member = memberRepository.findById(memberInfo.getMemberId())
            .orElseThrow(MemberNotExistException::new);

        final List<KillingPartLike> likes =
            killingPartLikeRepository.findAllByMemberAndIsDeleted(member, false);

        return likes.stream()
            .sorted(Comparator.comparing(KillingPartLike::getUpdatedAt).reversed())
            .map(killingPartLike -> {
                final KillingPart killingPart = killingPartLike.getKillingPart();
                return LikedKillingPartResponse.of(killingPart.getSong(), killingPart);
            }).toList();
    }
}
