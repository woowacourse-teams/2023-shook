package shook.shook.auth.oauth.application;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import shook.shook.auth.oauth.application.dto.GoogleAccessTokenRequest;
import shook.shook.auth.oauth.application.dto.GoogleAccessTokenResponse;
import shook.shook.auth.oauth.application.dto.GoogleMemberInfoResponse;
import shook.shook.auth.oauth.exception.OAuthException;

@RequiredArgsConstructor
@Service
public class GoogleInfoProvider {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String AUTHORIZATION_HEADER = "Authorization";

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

    public GoogleMemberInfoResponse getMemberInfo(final String accessToken) {
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.set(AUTHORIZATION_HEADER, TOKEN_PREFIX + accessToken);
            final HttpEntity<Object> request = new HttpEntity<>(headers);

            final GoogleMemberInfoResponse responseEntity = restTemplate.exchange(
                GOOGLE_MEMBER_INFO_URL,
                HttpMethod.GET,
                request,
                GoogleMemberInfoResponse.class).getBody();

            if (!Objects.requireNonNull(responseEntity).isVerifiedEmail()) {
                throw new OAuthException.InvalidEmailException();
            }

            return responseEntity;
        } catch (HttpClientErrorException e) {
            throw new OAuthException.InvalidAccessTokenException();
        } catch (HttpServerErrorException e) {
            throw new OAuthException.GoogleServerException();
        }
    }

    public GoogleAccessTokenResponse getAccessToken(final String authorizationCode) {
        try {
            final GoogleAccessTokenRequest googleAccessTokenRequest = new GoogleAccessTokenRequest(
                authorizationCode, GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET, LOGIN_REDIRECT_URL,
                GRANT_TYPE);
            final HttpEntity<GoogleAccessTokenRequest> request = new HttpEntity<>(
                googleAccessTokenRequest);

            return Objects.requireNonNull(restTemplate.postForEntity(
                GOOGLE_ACCESS_TOKEN_URL,
                request,
                GoogleAccessTokenResponse.class).getBody());

        } catch (HttpClientErrorException e) {
            throw new OAuthException.InvalidAuthorizationCodeException();
        } catch (HttpServerErrorException e) {
            throw new OAuthException.GoogleServerException();
        }
    }
}
