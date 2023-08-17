package shook.shook.auth.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import shook.shook.auth.application.TokenProvider;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.support.DataCleaner;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AccessTokenReissueControllerTest {

    @Autowired
    private DataCleaner dataCleaner;

    @LocalServerPort
    private int port;

    private static Member savedMember;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        dataCleaner.clear();
        savedMember = memberRepository.save(new Member("shook@wooteco.com", "shook"));
    }

    @AfterEach
    void delete() {
        memberRepository.delete(savedMember);
    }

    @DisplayName("올바른 refreshToken을 통해 accessToken 재발급을 요청하면 accessToken과 상태코드 200을 반환한다.")
    @Test
    void success_reissue_accessToken() {
        //given
        final String refreshToken = tokenProvider.createRefreshToken(
            savedMember.getId(),
            savedMember.getNickname());

        //when
        final ReissueAccessTokenResponse response = RestAssured.given().log().all()
            .cookie("refreshToken", refreshToken)
            .when().log().all().get("/reissue")
            .then().statusCode(HttpStatus.OK.value())
            .extract().body().as(ReissueAccessTokenResponse.class);

        //then
        final String accessToken = tokenProvider.createAccessToken(
            savedMember.getId(),
            savedMember.getNickname());

        assertThat(response.getAccessToken()).isEqualTo(accessToken);
    }

    // TODO: 2023/08/11 예외 상태코드가 정해지면 쿠키가 없는 경우 테스트 코드 추가하기
}
