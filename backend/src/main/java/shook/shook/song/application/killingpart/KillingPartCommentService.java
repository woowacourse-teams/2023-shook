package shook.shook.song.application.killingpart;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException.MemberNotExistException;
import shook.shook.song.application.killingpart.dto.KillingPartCommentRegisterRequest;
import shook.shook.song.application.killingpart.dto.KillingPartCommentResponse;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartComment;
import shook.shook.song.domain.killingpart.repository.KillingPartCommentRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.exception.killingpart.KillingPartException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class KillingPartCommentService {

    private final KillingPartRepository killingPartRepository;
    private final KillingPartCommentRepository killingPartCommentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void register(final long partId, final KillingPartCommentRegisterRequest request,
        final Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotExistException::new);

        final KillingPart killingPart = killingPartRepository.findById(partId)
            .orElseThrow(KillingPartException.PartNotExistException::new);

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
            .orElseThrow(KillingPartException.PartNotExistException::new);

        return KillingPartCommentResponse.ofComments(killingPart.getCommentsInRecentOrder());
    }
}
