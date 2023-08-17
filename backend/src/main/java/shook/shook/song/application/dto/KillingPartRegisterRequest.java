package shook.shook.song.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.part.domain.PartLength;
import shook.shook.song.domain.killingpart.KillingPart;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class KillingPartRegisterRequest {

    @NotNull
    @PositiveOrZero
    private Integer startSecond;

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
