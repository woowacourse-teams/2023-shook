package shook.shook.part.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.part.domain.PartComment;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PartCommentResponse {

    private final Long id;
    private final String content;
    private final LocalDateTime createdAt;

    public static PartCommentResponse from(final PartComment partComment) {
        return new PartCommentResponse(
            partComment.getId(),
            partComment.getContent(),
            partComment.getCreatedAt()
        );
    }

    public static List<PartCommentResponse> getList(final List<PartComment> partComments) {
        return partComments.stream()
            .map(PartCommentResponse::from)
            .toList();
    }
}
