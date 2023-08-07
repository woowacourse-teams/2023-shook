package shook.shook.auth.oauth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shook.shook.auth.jwt.application.TokenProvider;
import shook.shook.auth.oauth.application.dto.GoogleAccessTokenResponse;
import shook.shook.auth.oauth.application.dto.GoogleMemberInfoResponse;
import shook.shook.auth.oauth.application.dto.OAuthResponse;
import shook.shook.member.application.MemberService;
import shook.shook.member.application.dto.MemberRegisterRequest;

@SpringBootTest
class OAuthServiceTest {

    @Mock
    private GoogleInfoProvider googleInfoProvider;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenProvider tokenProvider;

    private OAuthService oAuthService;

    @BeforeEach
    void setUp() {
        oAuthService = new OAuthService(memberService, googleInfoProvider, tokenProvider);
    }

    @DisplayName("회원인 경우 email, accessToken과 refreshToken을 반환한다.")
    @Test
    void success_login() {
        //given
        memberService.register(new MemberRegisterRequest("shook@wooteco.com", "shook"));

        final GoogleAccessTokenResponse accessTokenResponse =
            new GoogleAccessTokenResponse("accessToken");
        when(googleInfoProvider.getAccessToken(any(String.class)))
            .thenReturn(accessTokenResponse);

        final GoogleMemberInfoResponse memberInfoResponse =
            new GoogleMemberInfoResponse("shook@wooteco.com", true);
        when(googleInfoProvider.getMemberInfo(any(String.class)))
            .thenReturn(memberInfoResponse);

        //when
        final OAuthResponse result = oAuthService.login("accessCode");

        //then
        assertThat(result.getEmail()).isEqualTo("shook@wooteco.com");
        assertThat(result.getAccessToken()).isNotNull();
        assertThat(result.getRefreshToken()).isNotNull();
    }

    @DisplayName("회원이 아닌 경우 accessToken과 refreshToken은 null을 반환한다.")
    @Test
    void fail_login() {
        //given
        final GoogleAccessTokenResponse accessTokenResponse =
            new GoogleAccessTokenResponse("accessToken");
        when(googleInfoProvider.getAccessToken(any(String.class)))
            .thenReturn(accessTokenResponse);

        final GoogleMemberInfoResponse memberInfoResponse =
            new GoogleMemberInfoResponse("shook@wooteco.com", true);
        when(googleInfoProvider.getMemberInfo(any(String.class)))
            .thenReturn(memberInfoResponse);

        //when
        final OAuthResponse result = oAuthService.login("accessCode");

        //then
        assertThat(result.getEmail()).isEqualTo("shook@wooteco.com");
        assertThat(result.getAccessToken()).isNull();
        assertThat(result.getRefreshToken()).isNull();
    }
}
