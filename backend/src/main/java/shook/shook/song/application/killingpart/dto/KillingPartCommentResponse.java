package shook.shook.song.application.killingpart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.killingpart.KillingPartComment;

@Schema(description = "킬링파트 댓글 조회 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class KillingPartCommentResponse {

    @Schema(description = "킬링파트 댓글 id", example = "1")
    private final Long id;

    @Schema(description = "킬링파트 댓글 내용", example = "댓글 내용")
    private final String content;

    @Schema(description = "댓글 작성자 닉네임", example = "닉네임")
    private final String writerNickname;

    @Schema(description = "킬링파트 댓글 등록 시간", example = "2023-08-15T16:34:30.388")
    private final LocalDateTime createdAt;

    public static KillingPartCommentResponse from(final KillingPartComment killingPartComment) {
        return new KillingPartCommentResponse(
            killingPartComment.getId(),
            killingPartComment.getContent(),
            killingPartComment.getWriterNickname(),
            killingPartComment.getCreatedAt()
        );
    }

    public static List<KillingPartCommentResponse> ofComments(
        final List<KillingPartComment> killingPartComments
    ) {
        return killingPartComments.stream()
            .map(KillingPartCommentResponse::from)
            .toList();
    }
}
