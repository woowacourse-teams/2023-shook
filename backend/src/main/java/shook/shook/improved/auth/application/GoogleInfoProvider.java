package shook.shook.improved.auth.application;

import java.util.HashMap;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import shook.shook.improved.auth.application.dto.GoogleAccessTokenResponse;
import shook.shook.improved.auth.application.dto.GoogleMemberInfoResponse;
import shook.shook.improved.auth.exception.OAuthException.GoogleServerException;
import shook.shook.improved.auth.exception.OAuthException.InvalidAccessTokenException;
import shook.shook.improved.auth.exception.OAuthException.InvalidAuthorizationCodeException;

@RequiredArgsConstructor
@Component
public class GoogleInfoProvider implements OAuthInfoProvider {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String GRANT_TYPE = "authorization_code";
    private final RestTemplate restTemplate;
    @Value("${oauth2.google.access-token-url}")
    private String GOOGLE_ACCESS_TOKEN_URL;
    @Value("${oauth2.google.member-info-url}")
    private String GOOGLE_MEMBER_INFO_URL;
    @Value("${oauth2.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${oauth2.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${oauth2.google.redirect-uri}")
    private String LOGIN_REDIRECT_URL;

    @Override
    public String getMemberInfo(final String accessToken) {
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + accessToken);
            final HttpEntity<Object> request = new HttpEntity<>(headers);

            final ResponseEntity<GoogleMemberInfoResponse> response = restTemplate.exchange(
                GOOGLE_MEMBER_INFO_URL,
                HttpMethod.GET,
                request,
                GoogleMemberInfoResponse.class
            );

            return Objects.requireNonNull(response.getBody()).getEmail();
        } catch (HttpClientErrorException e) {
            throw new InvalidAccessTokenException();
        } catch (HttpServerErrorException | NullPointerException e) {
            throw new GoogleServerException();
        }
    }

    @Override
    public String getAccessToken(final String authorizationCode) {
        try {
            final HashMap<String, String> params = new HashMap<>();
            params.put("code", authorizationCode);
            params.put("client_id", GOOGLE_CLIENT_ID);
            params.put("client_secret", GOOGLE_CLIENT_SECRET);
            params.put("redirect_uri", LOGIN_REDIRECT_URL);
            params.put("grant_type", GRANT_TYPE);

            final ResponseEntity<GoogleAccessTokenResponse> response = restTemplate.postForEntity(
                GOOGLE_ACCESS_TOKEN_URL,
                params,
                GoogleAccessTokenResponse.class);

            return Objects.requireNonNull(response.getBody()).getAccessToken();

        } catch (HttpClientErrorException e) {
            throw new InvalidAuthorizationCodeException();
        } catch (HttpServerErrorException | NullPointerException e) {
            throw new GoogleServerException();
        }
    }
}
