package shook.shook.auth.application;

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
import shook.shook.auth.application.dto.GoogleAccessTokenResponse;
import shook.shook.auth.application.dto.GoogleMemberInfoResponse;
import shook.shook.auth.exception.OAuthException;

@RequiredArgsConstructor
@Component
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

    @Value("${oauth2.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${oauth2.google.redirect-uri}")
    private String LOGIN_REDIRECT_URL;

    private final RestTemplate restTemplate;

    public GoogleMemberInfoResponse getMemberInfo(final String accessToken) {
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.set(AUTHORIZATION_HEADER, TOKEN_PREFIX + accessToken);
            final HttpEntity<Object> request = new HttpEntity<>(headers);

            //더 확실한 네이밍으로 가져가도 될 것 같아요~
            final GoogleMemberInfoResponse googleMemberInfoResponse = restTemplate.exchange(
                GOOGLE_MEMBER_INFO_URL,
                HttpMethod.GET,
                request,
                GoogleMemberInfoResponse.class
            ).getBody();

            //이 부분에서 body가 없을 시 NPE 발생은 의도하신건지 궁금해요~
            if (!Objects.requireNonNull(googleMemberInfoResponse).isVerifiedEmail()) {
                throw new OAuthException.InvalidEmailException();
            }
            return googleMemberInfoResponse;
        } catch (HttpClientErrorException e) {
            throw new OAuthException.InvalidAccessTokenException();
        } catch (HttpServerErrorException e) {
            throw new OAuthException.GoogleServerException();
        }
    }

    public GoogleAccessTokenResponse getAccessToken(final String authorizationCode) {
        try {
            //이 부분은 전체적으로 로직이 잘 못 되어있더라구요
            //제가 여러번 테스트해 본 결과 Token 요청은 본문이 아닌 param 으로 요청해야 하는 api 형식이에요~
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

            return Objects.requireNonNull(response.getBody());

        } catch (HttpClientErrorException e) {
            throw new OAuthException.InvalidAuthorizationCodeException();
        } catch (HttpServerErrorException e) {
            throw new OAuthException.GoogleServerException();
        }
    }
}
