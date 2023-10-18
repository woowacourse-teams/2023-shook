package shook.shook.auth.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.application.TokenService;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
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
        tokenService.validateRefreshToken(refreshToken);
        final String accessToken = tokenService.extractAccessToken(authorization);
        final ReissueAccessTokenResponse response = tokenService.reissueAccessTokenByRefreshToken(refreshToken,
                                                                                                  accessToken);

        return ResponseEntity.ok(response);
    }
}
