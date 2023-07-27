package shook.shook.part.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.part.domain.PartComment;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class PartCommentsResponse {

    private List<PartCommentResponse> partReplies;

    public static PartCommentsResponse of(final List<PartComment> replies) {
        final List<PartCommentResponse> commentResponses = replies.stream()
            .map(PartCommentResponse::from)
            .toList();

        return new PartCommentsResponse(commentResponses);
    }
}
