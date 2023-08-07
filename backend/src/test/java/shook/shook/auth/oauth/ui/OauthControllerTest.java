package shook.shook.auth.oauth.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import shook.shook.auth.oauth.application.OAuthService;
import shook.shook.auth.oauth.application.dto.OAuthResponse;

@WebMvcTest(OauthController.class)
class OauthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OAuthService oAuthService;

    @DisplayName("회원이 로그인을 하면 요청결과 200을 반환한다.")
    @Test
    void login_success() throws Exception {
        //given
        final OAuthResponse response = new OAuthResponse(
            "shook@wooteco.com",
            "asdfafdv2",
            "asdfsg5");

        when(oAuthService.login(any(String.class))).thenReturn(response);

        //when
        //then
        mockMvc.perform(get("/login/google").param("code", "accessCode"))
            .andExpect(status().isOk());
    }

    @DisplayName("회원이 아닌 사용자가 로그인을 하면 요청결과 401을 반환한다.")
    @Test
    void login_fail() throws Exception {
        //given
        final OAuthResponse response = new OAuthResponse(
            "shook@wooteco.com",
            null,
            null);

        when(oAuthService.login(any(String.class))).thenReturn(response);

        //when
        //then
        mockMvc.perform(get("/login/google").param("code", "accessCode"))
            .andExpect(status().isUnauthorized());
    }
}
