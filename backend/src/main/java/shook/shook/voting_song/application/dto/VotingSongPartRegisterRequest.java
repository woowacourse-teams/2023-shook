package shook.shook.voting_song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "파트 등록 요청")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class VotingSongPartRegisterRequest {

    @Schema(description = "파트 시작 초", example = "20")
    @NotNull
    @PositiveOrZero
    private Integer startSecond;

    @Schema(description = "파트 길이 초", example = "10")
    @NotNull
    @Positive
    private Integer length;
}
