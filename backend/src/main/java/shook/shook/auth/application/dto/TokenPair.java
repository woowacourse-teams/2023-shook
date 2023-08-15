package shook.shook.auth.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "로그인 응답")
@AllArgsConstructor
@Getter
public class TokenPair {

    @Schema(description = "액세스 토큰", example = "asdfjalwkejlfkejagalkwelf")
    private String accessToken;

    @Schema(description = "리프레시 토큰", example = "siehgaleihglawknelfkjeiewofj")
    private String refreshToken;
}
