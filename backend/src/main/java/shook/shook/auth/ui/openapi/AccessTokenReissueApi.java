package shook.shook.auth.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;

@Tag(name = "AccessTokenReissue", description = "액세스 토큰 재발급 API")
public interface AccessTokenReissueApi {

    @Operation(
        summary = "액세스 토큰 재발급 API",
        description = "액세스 토큰이 만료된 경우, 리프레시 토큰을 통해 액세스 토큰을 재발급한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "액세스 토큰 재발급 성공"
    )
    @Parameter(
        name = "refreshToken",
        description = "리프레시 토큰",
        required = true
    )
    @GetMapping("/reissue")
    ResponseEntity<ReissueAccessTokenResponse> reissueAccessToken(
        final String refreshToken
    );
}
