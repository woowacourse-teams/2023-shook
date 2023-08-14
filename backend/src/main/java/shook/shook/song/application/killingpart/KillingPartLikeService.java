package shook.shook.song.application.killingpart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.exception.killingpart.KillingPartException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class KillingPartLikeService {

    private final KillingPartRepository killingPartRepository;
    private final MemberRepository memberRepository;
    private final KillingPartLikeRepository likeRepository;

    @Transactional
    public void create(final Long killingPartId, final Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberException::new);

        final KillingPart killingPart = killingPartRepository.findById(killingPartId)
            .orElseThrow(KillingPartException.PartNotExistException::new);

        if (killingPart.findLikeByMember(member).isPresent()) {
            return;
        }

        final KillingPartLike likeOnKillingPart = likeRepository
            .findByKillingPartAndMember(killingPart, member)
            .orElseGet(() -> createNewLike(killingPart, member));

        killingPart.like(likeOnKillingPart);
    }

    private KillingPartLike createNewLike(final KillingPart killingPart, final Member member) {
        final KillingPartLike like = new KillingPartLike(killingPart, member);
        return likeRepository.save(like);
    }

    @Transactional
    public void delete(final Long killingPartId, final Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberException::new);

        final KillingPart killingPart = killingPartRepository.findById(killingPartId)
            .orElseThrow(KillingPartException.PartNotExistException::new);

        killingPart.findLikeByMember(member)
            .ifPresent(like -> killingPart.unlike(like));
    }

}
