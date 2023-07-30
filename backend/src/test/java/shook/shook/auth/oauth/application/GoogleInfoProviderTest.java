package shook.shook.auth.oauth.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import shook.shook.auth.oauth.exception.OAuthException;

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

    @Autowired
    private ObjectMapper mapper;

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
            .expect(requestTo(MEMBER_INFO_URL + "code"))
            .andRespond(withBadRequest());

        //when
        //then
        assertThatThrownBy(() -> googleInfoProvider.getMemberInfo("code"))
            .isInstanceOf(OAuthException.InvalidAccessTokenException.class);
    }

    @DisplayName("이메일이 유효하지 않으면 예외를 던진다.")
    @Test
    void fail_request_InvalidEmail() {
        //given
        final String response = """
            { email: shook@wooteco.com \n
            email_valid: false
            }
            """;

        mockServer
            .expect(requestTo(MEMBER_INFO_URL + "code"))
            .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        //when
        //then
        assertThatThrownBy(() -> googleInfoProvider.getMemberInfo("code"))
            .isInstanceOf(OAuthException.InvalidAccessTokenException.class);
    }
}

