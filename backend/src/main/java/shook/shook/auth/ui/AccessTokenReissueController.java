package shook.shook.auth.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.application.AuthService;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.auth.exception.AuthorizationException;
import shook.shook.auth.ui.openapi.AccessTokenReissueApi;

@RequiredArgsConstructor
@RestController
public class AccessTokenReissueController implements AccessTokenReissueApi {

    private static final String EMPTY_REFRESH_TOKEN = "none";
    private static final String REFRESH_TOKEN_KEY = "refreshToken";

    private final AuthService authService;

    @GetMapping("/reissue")
    public ResponseEntity<ReissueAccessTokenResponse> reissueAccessToken(
        @CookieValue(value = REFRESH_TOKEN_KEY, defaultValue = EMPTY_REFRESH_TOKEN) final String refreshToken
    ) {
        if (refreshToken.equals(EMPTY_REFRESH_TOKEN)) {
            throw new AuthorizationException.RefreshTokenNotFoundException();
        }
        final ReissueAccessTokenResponse response =
            authService.reissueAccessTokenByRefreshToken(refreshToken);

        return ResponseEntity.ok(response);
    }
}
