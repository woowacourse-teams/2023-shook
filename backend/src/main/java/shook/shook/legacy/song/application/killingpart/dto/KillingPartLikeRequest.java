package shook.shook.legacy.song.application.killingpart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "킬링파트 좋아요 요청")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class KillingPartLikeRequest {

    @Schema(description = "좋아요 여부", example = "true")
    @NotNull
    private Boolean likeStatus;

    public boolean isLikeCreateRequest() {
        return likeStatus;
    }
}
