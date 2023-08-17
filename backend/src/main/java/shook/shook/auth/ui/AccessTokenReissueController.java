package shook.shook.auth.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.application.GoogleAuthService;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.auth.exception.AuthorizationException;

@RequiredArgsConstructor
@RestController
//컨트롤러의 역할을 네이밍에 자세히 나타내는 것도 괜찮을 것 같아요~
public class AccessTokenReissueController {

    private static final String EMPTY_REFRESH_TOKEN = "none";
    private static final String REFRESH_TOKEN_KEY = "refreshToken";

    private final GoogleAuthService googleAuthService;

    @GetMapping("/reissue")
    public ResponseEntity<ReissueAccessTokenResponse> reissueAccessToken(
        //상수로 분리해보는건 어떨까요??
        @CookieValue(value = REFRESH_TOKEN_KEY, defaultValue = EMPTY_REFRESH_TOKEN) final String refreshToken
    ) {
        if (refreshToken.equals(REFRESH_TOKEN_KEY)) {
            throw new AuthorizationException.RefreshTokenNotFoundException();
        }
        final ReissueAccessTokenResponse response =
            googleAuthService.reissueAccessTokenByRefreshToken(refreshToken);

        return ResponseEntity.ok(response);
    }
}
