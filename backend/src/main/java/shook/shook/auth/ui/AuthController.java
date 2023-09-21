package shook.shook.auth.ui;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.application.AuthService;
import shook.shook.auth.application.dto.TokenPair;
import shook.shook.auth.ui.dto.LoginResponse;
import shook.shook.auth.ui.openapi.AuthApi;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;
    private final CookieProvider cookieProvider;

    @GetMapping("{oauthType}/redirect")
    public ResponseEntity<LoginResponse> googleLogin(
        @RequestParam("code") final String authorizationCode,
        @PathVariable("oauthType") final String oauthType,
        final HttpServletResponse response
    ) {
        final TokenPair tokenPair = authService.oAuthLogin(oauthType, authorizationCode);
        final Cookie cookie = cookieProvider.createRefreshTokenCookie(tokenPair.getRefreshToken());
        response.addCookie(cookie);
        final LoginResponse loginResponse = new LoginResponse(tokenPair.getAccessToken());
        return ResponseEntity.ok(loginResponse);
    }
}
