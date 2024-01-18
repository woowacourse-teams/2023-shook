package shook.shook.legacy.member_part.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "멤버 파트 등록 요청")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class MemberPartRegisterRequest {

    @Schema(description = "멤버 파트 시작 초", example = "23")
    @NotNull
    @PositiveOrZero
    private Integer startSecond;

    @Schema(description = "멤버 파트 길이", example = "10")
    @NotNull
    @Positive
    private Integer length;
}
