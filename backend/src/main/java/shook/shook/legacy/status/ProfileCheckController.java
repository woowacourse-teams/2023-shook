package shook.shook.legacy.status;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.legacy.status.response.ProfileResponse;

@RestController
@RequestMapping("/status/profile")
@RequiredArgsConstructor
public class ProfileCheckController {

    private final Environment environment;

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {
        return ResponseEntity.ok(new ProfileResponse(environment.getActiveProfiles()));
    }
}
