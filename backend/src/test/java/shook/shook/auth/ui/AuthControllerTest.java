package shook.shook.auth.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import shook.shook.auth.application.AuthService;
import shook.shook.auth.application.dto.LoginResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @LocalServerPort
    public int port;

    @MockBean
    private AuthService authService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("회원이 로그인을 하면 요청결과 200을 반환한다.")
    @Test
    void login_success() {
        //given
        final LoginResponse response = new LoginResponse(
            "asdfafdv2",
            "asdfsg5");

        when(authService.login(any(String.class))).thenReturn(response);

        //when
        //then
        final LoginResponse actualResponse = RestAssured.given().log().all()
            .when().log().all().get("/login/google?code=accessCode")
            .then().statusCode(HttpStatus.OK.value())
            .extract().body().as(LoginResponse.class);

        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(response);
    }
}
