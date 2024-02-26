package shook.shook.improved.auth.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 응답")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class LoginResponse {

    @Schema(description = "액세스 토큰", example = "aliehgklasejf")
    private String accessToken;
}
