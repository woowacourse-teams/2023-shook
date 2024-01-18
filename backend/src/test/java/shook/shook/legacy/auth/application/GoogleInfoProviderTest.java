package shook.shook.legacy.auth.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;
import shook.shook.legacy.auth.exception.OAuthException;

@AutoConfigureWebClient(registerRestTemplate = true)
@RestClientTest(value = {GoogleInfoProvider.class})
class GoogleInfoProviderTest {

    @Value("${oauth2.google.access-token-url}")
    private String ACCESS_TOKEN_URL;

    @Value("${oauth2.google.member-info-url}")
    private String MEMBER_INFO_URL;

    @Autowired
    private GoogleInfoProvider googleInfoProvider;

    @Autowired
    private MockRestServiceServer mockServer;

    @DisplayName("올바르지 않은 authorizedCode를 통해 요청을 보내면 예외를 던진다.")
    @Test
    void fail_request_accessToken() {
        //given
        mockServer
            .expect(requestTo(ACCESS_TOKEN_URL))
            .andRespond(withBadRequest());

        //when
        //then
        assertThatThrownBy(() -> googleInfoProvider.getAccessToken("code"))
            .isInstanceOf(OAuthException.InvalidAuthorizationCodeException.class);
    }

    @DisplayName("올바르지 않은 accessToken으로 요청을 보내면 예외를 던진다.")
    @Test
    void fail_request_memberInfo_InvalidAccessToken() {
        //given
        mockServer
            .expect(requestTo(MEMBER_INFO_URL))
            .andRespond(withBadRequest());

        //when
        //then
        assertThatThrownBy(() -> googleInfoProvider.getMemberInfo("code"))
            .isInstanceOf(OAuthException.InvalidAccessTokenException.class);
    }

    @DisplayName("accessToken을 요청할 때 구글 서버에러가 발생하면 예외를 던진다.")
    @Test
    void fail_access_token_request_google_server_error() {
        //given
        mockServer
            .expect(requestTo(ACCESS_TOKEN_URL))
            .andRespond(withServerError());

        //when
        //then
        assertThatThrownBy(() -> googleInfoProvider.getAccessToken("code"))
            .isInstanceOf(OAuthException.GoogleServerException.class);
    }

    @DisplayName("memberInfo를 요청할 때 구글 서버에러가 발생하면 예외를 던진다.")
    @Test
    void fail_memberInfo_request_google_server_error() {
        //given
        mockServer
            .expect(requestTo(MEMBER_INFO_URL))
            .andRespond(withServerError());

        //when
        //then
        assertThatThrownBy(() -> googleInfoProvider.getMemberInfo("code"))
            .isInstanceOf(OAuthException.GoogleServerException.class);
    }
}
