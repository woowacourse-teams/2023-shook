package shook.shook.auth.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import shook.shook.auth.application.AuthService;
import shook.shook.auth.application.dto.TokenPair;
import shook.shook.auth.ui.dto.LoginResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @LocalServerPort
    public int port;

    @Value("${cookie.valid-time}")
    private int cookieAge;

    @MockBean
    private AuthService authService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("회원이 소셜 로그인을 하면 요청결과로 accessToken은 ResponseBody로 refreshToken은 쿠키로 전달되며 상태코드 200을 반환한다.")
    @Test
    void login_success() {
        //given
        final TokenPair tokenPair = new TokenPair("asdfafdv2", "asdfsg5");

        when(authService.oAuthLogin(any(String.class), any(String.class))).thenReturn(tokenPair);

        //when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when().log().all().get("/login/google?code=accessCode")
            .then().log().all().statusCode(HttpStatus.OK.value())
            .extract();

        //then
        final LoginResponse expectResponseBody = new LoginResponse(tokenPair.getAccessToken());
        final LoginResponse responseBody = response.body().as(LoginResponse.class);
        final String cookie = response.header("Set-Cookie");

        assertAll(
            () -> assertThat(responseBody.getAccessToken()).isEqualTo(
                expectResponseBody.getAccessToken()),
            () -> assertThat(cookie.contains("refreshToken=asdfsg5")).isTrue(),
            () -> assertThat(cookie.contains("Max-Age=" + cookieAge)).isTrue(),
            () -> assertThat(cookie.contains("Path=/api")).isTrue(),
            () -> assertThat(cookie.contains("HttpOnly")).isTrue()
        );
    }
}
