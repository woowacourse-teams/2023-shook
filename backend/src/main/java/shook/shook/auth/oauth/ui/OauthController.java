package shook.shook.auth.oauth.ui;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.oauth.application.OAuthService;
import shook.shook.auth.oauth.application.dto.OAuthResponse;

@RequiredArgsConstructor
@RestController
public class OauthController {

    private final OAuthService oAuthService;

    @GetMapping("login/google")
    public ResponseEntity<OAuthResponse> googleLogin(@RequestParam("code") final String accessCode) {
        final OAuthResponse response = oAuthService.login(accessCode);
        if (Objects.isNull(response.getAccessToken()) && Objects.isNull(
            response.getRefreshToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
