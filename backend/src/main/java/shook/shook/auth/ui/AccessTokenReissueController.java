package shook.shook.auth.ui;

import static shook.shook.auth.application.TokenService.TOKEN_PREFIX;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.application.TokenService;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.auth.exception.AuthorizationException;
import shook.shook.auth.ui.openapi.AccessTokenReissueApi;

@RequiredArgsConstructor
@RestController
public class AccessTokenReissueController implements AccessTokenReissueApi {

    private static final String EMPTY_REFRESH_TOKEN = "none";
    private static final String REFRESH_TOKEN_KEY = "refreshToken";

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ResponseEntity<ReissueAccessTokenResponse> reissueAccessToken(
        @CookieValue(value = REFRESH_TOKEN_KEY, defaultValue = EMPTY_REFRESH_TOKEN) final String refreshToken,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String authorization
    ) {
        validateRefreshToken(refreshToken);
        final String accessToken = extractAccessToken(authorization);
        final ReissueAccessTokenResponse response = tokenService.reissueAccessTokenByRefreshToken(refreshToken,
                                                                                                  accessToken);

        return ResponseEntity.ok(response);
    }

    private void validateRefreshToken(final String refreshToken) {
        if (refreshToken.equals(EMPTY_REFRESH_TOKEN)) {
            throw new AuthorizationException.RefreshTokenNotFoundException();
        }
    }

    private String extractAccessToken(final String authorization) {
        return authorization.substring(TOKEN_PREFIX.length());
    }
}
