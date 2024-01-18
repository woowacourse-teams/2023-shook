package shook.shook.legacy.auth.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shook.shook.legacy.auth.ui.dto.LoginResponse;

@Tag(name = "OAuth", description = "구글 로그인 API")
public interface AuthApi {

    @Operation(
        summary = "구글 로그인 API",
        description = "구글 로그인을 진행한 후, accessToken, refreshToken을 발급한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "구글 로그인 성공"
    )
    @Parameters(
        value = {
            @Parameter(
                name = "code",
                description = "소셜 로그인 시 발급받은 인증 코드",
                required = true
            ),
            @Parameter(
                name = "oauthType",
                description = "소셜 로그인 타입",
                required = true
            )
        }

    )
    @GetMapping("/login/{oauthType}")
    ResponseEntity<LoginResponse> googleLogin(
        @RequestParam("code") final String authorizationCode,
        @PathVariable("oauthType") final String oauthType,
        final HttpServletResponse response
    );
}
