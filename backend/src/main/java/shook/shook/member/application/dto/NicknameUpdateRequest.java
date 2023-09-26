package shook.shook.member.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "닉네임 변경 요청")
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Getter
public class NicknameUpdateRequest {

    @Schema(description = "닉네임", example = "shookshook")
    @NotBlank
    private String nickname;
}
