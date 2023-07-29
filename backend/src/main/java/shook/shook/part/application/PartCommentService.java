package shook.shook.part.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.part.application.dto.PartCommentRegisterRequest;
import shook.shook.part.application.dto.PartCommentResponse;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartComment;
import shook.shook.part.domain.repository.PartCommentRepository;
import shook.shook.part.domain.repository.PartRepository;
import shook.shook.part.exception.PartException;
import shook.shook.part.exception.PartException.PartNotExistException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PartCommentService {

    private final PartRepository partRepository;
    private final PartCommentRepository partCommentRepository;

    @Transactional
    public void register(final Long partId, final PartCommentRegisterRequest request) {
        final Part part = partRepository.findById(partId)
            .orElseThrow(PartNotExistException::new);
        final PartComment partComment = PartComment.forSave(part, request.getContent());

        part.addComment(partComment);
        partCommentRepository.save(partComment);
    }

    public List<PartCommentResponse> findPartReplies(final Long partId) {
        final Part part = partRepository.findById(partId)
            .orElseThrow(PartException.PartNotExistException::new);

        return part.getCommentsInRecentOrder().stream()
            .map(PartCommentResponse::from)
            .toList();
    }
}
