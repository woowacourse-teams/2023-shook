package shook.shook.legacy.song.application.killingpart;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.legacy.member.domain.Member;
import shook.shook.legacy.member.domain.repository.MemberRepository;
import shook.shook.legacy.song.application.killingpart.dto.KillingPartCommentRegisterRequest;
import shook.shook.legacy.song.application.killingpart.dto.KillingPartCommentResponse;
import shook.shook.legacy.song.domain.killingpart.KillingPart;
import shook.shook.legacy.song.domain.killingpart.KillingPartComment;
import shook.shook.legacy.song.domain.killingpart.repository.KillingPartCommentRepository;
import shook.shook.legacy.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.member.exception.MemberException;
import shook.shook.song.exception.legacy_killingpart.KillingPartException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class KillingPartCommentService {

    private final KillingPartRepository killingPartRepository;
    private final KillingPartCommentRepository killingPartCommentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void register(
        final Long partId,
        final KillingPartCommentRegisterRequest request,
        final Long memberId
    ) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(
                () -> new MemberException.MemberNotExistException(
                    Map.of("MemberId", String.valueOf(memberId))
                )
            );

        final KillingPart killingPart = killingPartRepository.findById(partId)
            .orElseThrow(() -> new KillingPartException.PartNotExistException(
                Map.of("KillingPartId", String.valueOf(partId))
            ));

        final KillingPartComment killingPartComment = KillingPartComment.forSave(
            killingPart,
            request.getContent(),
            member
        );

        killingPart.addComment(killingPartComment);
        killingPartCommentRepository.save(killingPartComment);
    }

    public List<KillingPartCommentResponse> findKillingPartComments(final Long killingPartId) {
        final KillingPart killingPart = killingPartRepository.findById(killingPartId)
            .orElseThrow(() -> new KillingPartException.PartNotExistException(
                Map.of("KillingPartId", String.valueOf(killingPartId))
            ));

        return KillingPartCommentResponse.ofComments(killingPart.getCommentsInRecentOrder());
    }
}
