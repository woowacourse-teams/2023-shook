package shook.shook.auth.oauth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shook.shook.auth.oauth.application.dto.GoogleAccessTokenResponse;
import shook.shook.auth.oauth.application.dto.GoogleMemberInfoResponse;
import shook.shook.auth.oauth.application.dto.LoginResponse;
import shook.shook.member.application.MemberService;

@SpringBootTest
class OAuthServiceTest {

    @MockBean
    private GoogleInfoProvider googleInfoProvider;

    @Autowired
    private MemberService memberService;

    @Autowired
    private OAuthService oAuthService;

    @DisplayName("회원인 경우 email, accessToken과 refreshToken을 반환한다.")
    @Test
    void success_login() {
        //given
        memberService.register("shook@wooteco.com");

        final GoogleAccessTokenResponse accessTokenResponse =
            new GoogleAccessTokenResponse("accessToken");
        when(googleInfoProvider.getAccessToken(any(String.class)))
            .thenReturn(accessTokenResponse);

        final GoogleMemberInfoResponse memberInfoResponse =
            new GoogleMemberInfoResponse("shook@wooteco.com", true);
        when(googleInfoProvider.getMemberInfo(any(String.class)))
            .thenReturn(memberInfoResponse);

        //when
        final LoginResponse result = oAuthService.login("accessCode");

        //then
        assertThat(result.getAccessToken()).isNotNull();
        assertThat(result.getRefreshToken()).isNotNull();
    }
}
