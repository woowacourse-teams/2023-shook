package shook.shook.legacy.song.ui;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.legacy.auth.application.TokenProvider;
import shook.shook.legacy.song.application.killingpart.dto.KillingPartLikeRequest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class KillingPartLikeControllerTest {

    private static final long SAVED_KILLING_PART_ID = 1L;
    private static final long SAVED_SONG_ID = 1L;
    private static final long SAVED_MEMBER_ID = 1L;
    private static final String SAVED_MEMBER_NICKNAME = "nickname";
    private static final String TOKEN_PREFIX = "Bearer ";

    @LocalServerPort
    public int port;

    @Autowired
    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("킬링파트에 좋아요 등록 시 201 상태코드를 반환한다.")
    @Test
    void createLikeOnKillingPart() {
        // given
        final KillingPartLikeRequest request = new KillingPartLikeRequest(true);
        final String accessToken = tokenProvider.createAccessToken(
            SAVED_MEMBER_ID,
            SAVED_MEMBER_NICKNAME);

        // when, then
        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when().log().all()
            .put("/songs/{songId}/parts/{killingPartId}/likes", SAVED_SONG_ID,
                SAVED_KILLING_PART_ID)
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("킬링파트에 좋아요 취소 시 201 상태코드를 반환한다.")
    @Test
    void deleteLikeOnKillingPart() {
        // given
        final KillingPartLikeRequest request = new KillingPartLikeRequest(false);
        final String accessToken = tokenProvider.createAccessToken(
            SAVED_MEMBER_ID,
            SAVED_MEMBER_NICKNAME);

        // when, then
        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .param("memberId", SAVED_MEMBER_ID)
            .when().log().all()
            .put("/songs/{songId}/parts/{killingPartId}/likes", SAVED_SONG_ID,
                SAVED_KILLING_PART_ID)
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }
}
