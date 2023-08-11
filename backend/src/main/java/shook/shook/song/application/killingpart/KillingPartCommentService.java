package shook.shook.song.application.killingpart;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void register(final long partId, final KillingPartCommentRegisterRequest request) {
        final KillingPart killingPart = killingPartRepository.findById(partId)
            .orElseThrow(KillingPartException.PartNotExistException::new);
        final KillingPartComment killingPartComment = KillingPartComment.forSave(
            killingPart,
            request.getContent()
        );

        killingPart.addComment(killingPartComment);
        killingPartCommentRepository.save(killingPartComment);
    }

    public List<KillingPartCommentResponse> findKillingPartComments(final Long partId) {
        final KillingPart killingPart = killingPartRepository.findById(partId)
            .orElseThrow(KillingPartException.PartNotExistException::new);

        return KillingPartCommentResponse.getList(killingPart.getCommentsInRecentOrder());
    }
}
