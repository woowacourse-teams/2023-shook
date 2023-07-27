package shook.shook.part.application.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.part.domain.PartComment;

@AllArgsConstructor
@Getter
public class PartCommentResponse {

    private final String content;
    private final LocalDateTime createdAt;

    public static PartCommentResponse from(final PartComment partComment) {
        return new PartCommentResponse(partComment.getContent(), partComment.getCreatedAt());
    }
}
