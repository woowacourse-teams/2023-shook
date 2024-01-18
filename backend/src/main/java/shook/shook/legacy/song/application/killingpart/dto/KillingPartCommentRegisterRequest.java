package shook.shook.legacy.song.application.killingpart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "킬링파트 댓글 등록 요청")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class KillingPartCommentRegisterRequest {

    @Schema(description = "킬링파트 댓글 내용", example = "댓글 내용")
    @NotNull
    private String content;
}
