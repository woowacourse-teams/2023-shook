package shook.shook.auth.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.application.AuthService;
import shook.shook.auth.application.dto.LoginResponse;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/google")
    public ResponseEntity<LoginResponse> googleLogin(
        @RequestParam("code") final String accessCode) {
        final LoginResponse response = authService.login(accessCode);
        return ResponseEntity.ok(response);
    }
}
