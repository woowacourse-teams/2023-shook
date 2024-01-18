package shook.shook.auth.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.application.AuthService;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.auth.exception.AuthorizationException.RefreshTokenNotFoundException;
import shook.shook.auth.ui.openapi.AccessTokenReissueApi;

@RequiredArgsConstructor
@RestController
public class AccessTokenReissueController implements AccessTokenReissueApi {

    private static final String EMPTY_REFRESH_TOKEN = "none";
    private static final String REFRESH_TOKEN_KEY = "refreshToken";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseEntity<ReissueAccessTokenResponse> reissueAccessToken(
        @CookieValue(value = REFRESH_TOKEN_KEY, defaultValue = EMPTY_REFRESH_TOKEN) final String refreshToken,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String authorization
    ) {
        if (refreshToken.equals(EMPTY_REFRESH_TOKEN)) {
            throw new RefreshTokenNotFoundException();
        }
        final String accessToken = authorization.split(TOKEN_PREFIX)[1];
        final ReissueAccessTokenResponse response =
            authService.reissueAccessTokenByRefreshToken(refreshToken, accessToken);

        return ResponseEntity.ok(response);
    }
}
