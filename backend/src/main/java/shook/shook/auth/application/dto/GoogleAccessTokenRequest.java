package shook.shook.auth.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class GoogleAccessTokenRequest {

    private String code;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType;
}
