package shook.shook.auth.oauth.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OAuthResponse {

    private String email;
    private String accessToken;
    private String refreshToken;
}
