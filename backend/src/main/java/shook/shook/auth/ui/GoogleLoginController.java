package shook.shook.auth.ui;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.application.GoogleAuthService;
import shook.shook.auth.application.dto.TokenPair;
import shook.shook.auth.ui.dto.LoginResponse;

@RequiredArgsConstructor
@RestController
//만약 다른 Oauth 에서도 code 를 통한 인증방식을 사용한다면 OauthController 로 추상화할 수 있을 것 같아요
//우선은 내용이 구글 로그인이니까 그냥 의도를 나타내는 것도 나쁘지 않을 것 같아요~
public class GoogleLoginController {

    private final GoogleAuthService googleAuthService;
    private final CookieProvider cookieProvider;

    @GetMapping("/login/google")
    public ResponseEntity<LoginResponse> googleLogin(
        //access code 보다는 authorization code 라고 칭하더라구요~
        @RequestParam("code") final String authorizationCode,
        final HttpServletResponse response
    ) {
        final TokenPair tokenPair = googleAuthService.login(authorizationCode);
        final Cookie cookie = cookieProvider.createRefreshTokenCookie(tokenPair.getRefreshToken());
        response.addCookie(cookie);
        final LoginResponse loginResponse = new LoginResponse(tokenPair.getAccessToken());
        return ResponseEntity.ok(loginResponse);
    }
}
