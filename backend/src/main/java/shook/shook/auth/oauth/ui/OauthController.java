package shook.shook.auth.oauth.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.oauth.application.OAuthService;
import shook.shook.auth.oauth.application.dto.LoginResponse;

@RequiredArgsConstructor
@RestController
public class OauthController {

    private final OAuthService oAuthService;

    @GetMapping("login/google")
    public ResponseEntity<LoginResponse> googleLogin(
        @RequestParam("code") final String accessCode) {
        final LoginResponse response = oAuthService.login(accessCode);
        return ResponseEntity.ok(response);
    }
}
