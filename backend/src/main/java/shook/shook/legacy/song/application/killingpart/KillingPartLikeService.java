package shook.shook.legacy.song.application.killingpart;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.legacy.member.domain.Member;
import shook.shook.legacy.member.domain.repository.MemberRepository;
import shook.shook.legacy.song.application.killingpart.dto.KillingPartLikeRequest;
import shook.shook.legacy.song.domain.InMemorySongs;
import shook.shook.legacy.song.domain.killingpart.KillingPart;
import shook.shook.legacy.song.domain.killingpart.KillingPartLike;
import shook.shook.legacy.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.legacy.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.improved.member.exception.MemberException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartException.PartNotExistException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class KillingPartLikeService {

    private final KillingPartRepository killingPartRepository;
    private final MemberRepository memberRepository;
    private final KillingPartLikeRepository likeRepository;
    private final InMemorySongs inMemorySongs;

    @Transactional
    public void updateLikeStatus(
        final Long killingPartId,
        final Long memberId,
        final KillingPartLikeRequest request
    ) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException.MemberNotExistException(
                Map.of("MemberId", String.valueOf(memberId))
            ));

        final KillingPart killingPart = killingPartRepository.findById(killingPartId)
            .orElseThrow(() -> new PartNotExistException(
                Map.of("KillingPartId", String.valueOf(killingPartId))
            ));

        if (request.isLikeCreateRequest()) {
            create(killingPart, member);
            return;
        }
        delete(killingPart, member);
    }

    private void create(final KillingPart killingPart, final Member member) {
        if (killingPart.findLikeByMember(member).isPresent()) {
            return;
        }

        final KillingPartLike likeOnKillingPart = likeRepository.findByKillingPartAndMember(killingPart, member)
            .orElseGet(() -> createNewLike(killingPart, member));
        if (likeOnKillingPart.isDeleted()) {
            inMemorySongs.like(killingPart, likeOnKillingPart);
            likeRepository.pressLike(likeOnKillingPart.getId());
        }
    }

    private KillingPartLike createNewLike(final KillingPart killingPart, final Member member) {
        final KillingPartLike like = new KillingPartLike(killingPart, member);

        return likeRepository.save(like);
    }

    private void delete(final KillingPart killingPart, final Member member) {
        killingPart.findLikeByMember(member)
            .ifPresent(likeOnKillingPart -> {
                inMemorySongs.unlike(killingPart, likeOnKillingPart);
                likeRepository.cancelLike(likeOnKillingPart.getId());
            });
    }
}
