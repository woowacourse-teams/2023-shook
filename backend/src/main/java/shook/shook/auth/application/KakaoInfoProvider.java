package shook.shook.auth.application;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import shook.shook.auth.application.dto.KakaoAccessTokenResponse;
import shook.shook.auth.application.dto.KakaoMemberInfoResponse;
import shook.shook.auth.exception.OAuthException;

@RequiredArgsConstructor
@Component
public class KakaoInfoProvider implements OAuthInfoProvider {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${oauth2.kakao.access-token-url}")
    private String KAKAO_ACCESS_TOKEN_URL;

    @Value("${oauth2.kakao.member-info-url}")
    private String KAKAO_MEMBER_INFO_URL;

    @Value("${oauth2.kakao.client-id}")
    private String KAKAO_CLIENT_ID;

    @Value("${oauth2.kakao.redirect-url}")
    private String LOGIN_REDIRECT_URL;

    private final RestTemplate restTemplate;

    public String getMemberInfo(final String accessToken) {
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.set(AUTHORIZATION_HEADER, TOKEN_PREFIX + accessToken);

            final HttpEntity<Object> request = new HttpEntity<>(headers);
            final ResponseEntity<KakaoMemberInfoResponse> response = restTemplate.exchange(
                KAKAO_MEMBER_INFO_URL,
                HttpMethod.GET,
                request,
                KakaoMemberInfoResponse.class
            );

            return String.valueOf(Objects.requireNonNull(response.getBody()).getId());
        } catch (HttpClientErrorException e) {
            throw new OAuthException.InvalidAccessTokenException();
        } catch (HttpServerErrorException e) {
            throw new OAuthException.KakaoServerException();
        }
    }

    public String getAccessToken(final String authorizationCode) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("redirect_uri", LOGIN_REDIRECT_URL);
        params.add("code", authorizationCode);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        final ResponseEntity<KakaoAccessTokenResponse> response = restTemplate.postForEntity(
            KAKAO_ACCESS_TOKEN_URL,
            request,
            KakaoAccessTokenResponse.class);

        return Objects.requireNonNull(response.getBody()).getAccessToken();

    }
}
