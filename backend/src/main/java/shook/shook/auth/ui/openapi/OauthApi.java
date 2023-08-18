package shook.shook.auth.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shook.shook.auth.ui.dto.LoginResponse;

@Tag(name = "OAuth", description = "구글 로그인 API")
public interface OauthApi {

    @Operation(
        summary = "구글 로그인 API",
        description = "구글 로그인을 진행한 후, accessToken, refreshToken을 발급한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "구글 로그인 성공"
    )
    @Parameter(
        name = "code",
        description = "구글 로그인 후 발급받은 인증 코드",
        required = true
    )
    @GetMapping("/login/google")
    ResponseEntity<LoginResponse> googleLogin(
        @RequestParam("code") final String accessCode,
        final HttpServletResponse response
    );
}
