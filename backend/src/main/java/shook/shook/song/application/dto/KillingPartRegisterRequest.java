package shook.shook.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.killingpart.KillingPart;

@Schema(description = "킬링파트 등록 요청")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class KillingPartRegisterRequest {

    @Schema(description = "킬링파트 시작 초", example = "30")
    @NotNull
    @PositiveOrZero
    private Integer startSecond;

    @Schema(description = "킬링파트 길이", example = "10")
    @NotNull
    @Positive
    private Integer length;

    public KillingPart toKillingPart() {
        return KillingPart.forSave(
            startSecond,
            PartLength.findBySecond(length)
        );
    }
}
