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
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.song.domain.killingpart.repository.dto.SongKillingPartKillingPartLikeCreatedAtDto;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MyPageService {

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

        final List<SongKillingPartKillingPartLikeCreatedAtDto> likedKillingPartAndSongByMember =
            killingPartLikeRepository.findLikedKillingPartAndSongByMember(member);

        return likedKillingPartAndSongByMember.stream()
            .sorted(Comparator.comparing(
                SongKillingPartKillingPartLikeCreatedAtDto::getKillingPartLikeCreatedAt,
                Comparator.reverseOrder()
            ))
            .map(songKillingPart -> LikedKillingPartResponse.of(
                songKillingPart.getSong(),
                songKillingPart.getKillingPart()
            ))
            .toList();
    }
}
