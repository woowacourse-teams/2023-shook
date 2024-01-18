package shook.shook.legacy.auth.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "액세스 토큰 재발급 응답")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReissueAccessTokenResponse {

    @Schema(name = "액세스 토큰", example = "alkwuehflekflksej")
    private String accessToken;
}
