package shook.shook.auth.oauth.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import shook.shook.auth.oauth.application.dto.GoogleAccessTokenResponse;
import shook.shook.auth.oauth.application.dto.GoogleMemberInfoResponse;
import shook.shook.auth.oauth.exception.OAuthException;

@RequiredArgsConstructor
@Service
public class GoogleInfoProvider {

    @Value("${oauth2.google.access-token-url}")
    private String GOOGLE_ACCESS_TOKEN_URL;

    @Value("${oauth2.google.member-info-url}")
    private String GOOGLE_MEMBER_INFO_URL;

    @Value("${oauth2.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value(value = "${oauth2.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value(value = "${oauth2.google.redirect-uri}")
    private String LOGIN_REDIRECT_URL;

    private final RestTemplate restTemplate;

    public ResponseEntity<GoogleMemberInfoResponse> getMemberInfo(final String accessToken) {
        try {
            final ResponseEntity<GoogleMemberInfoResponse> responseEntity = restTemplate.getForEntity(
                GOOGLE_MEMBER_INFO_URL + accessToken,
                GoogleMemberInfoResponse.class);
            if (!Objects.requireNonNull(responseEntity.getBody()).isVerifiedEmail()) {
                throw new OAuthException.InvalidEmailException();
            }

            return responseEntity;
        } catch (RestClientException e) {
            throw new OAuthException.InvalidAccessTokenException();
        }
    }

    public ResponseEntity<GoogleAccessTokenResponse> getAccessToken(final String accessCode) {
        try {
            final Map<String, String> params = new HashMap<>();

            params.put("code", accessCode);
            params.put("client_id", GOOGLE_CLIENT_ID);
            params.put("client_secret", GOOGLE_CLIENT_SECRET);
            params.put("redirect_uri", LOGIN_REDIRECT_URL);
            params.put("grant_type", "authorization_code");

            final ResponseEntity<GoogleAccessTokenResponse> responseEntity = restTemplate.postForEntity(
                GOOGLE_ACCESS_TOKEN_URL,
                params,
                GoogleAccessTokenResponse.class);

            return responseEntity;
        } catch (RestClientException e) {
            throw new OAuthException.InvalidAuthorizationCodeException();
        }
    }
}
