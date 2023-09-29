package shook.shook.member.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "닉네임 변경 응답")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class NicknameUpdateResponse {

    @Schema(description = "액세스 토큰", example = "lfahrg;aoiehflksejflakwjeglk")
    private String accessToken;

    @Schema(description = "변경된 닉네임", example = "shook")
    private String nickname;
}
