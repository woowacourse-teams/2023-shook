package shook.shook.song.ui;

import static org.hamcrest.Matchers.matchesPattern;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import shook.shook.song.application.dto.KillingPartRegisterRequest;
import shook.shook.song.application.dto.SongWithKillingPartsRegisterRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AdminSongControllerTest {

    private static final String LOCATION_HEADER_WITH_ID_REGEX = "/\\d+";

    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("노래와 킬링파트 등록시 201 상태코드, 생성된 노래의 id 가 담긴 Location 을 반환한다.")
    @Test
    void register_success() {
        // given
        final SongWithKillingPartsRegisterRequest request = new SongWithKillingPartsRegisterRequest(
            "title", "videoUrl", "imageUrl", "singer", 300,
            List.of(
                new KillingPartRegisterRequest(10, 5),
                new KillingPartRegisterRequest(15, 10),
                new KillingPartRegisterRequest(0, 10)
            )
        );

        // when, then
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when().log().all()
            .post("/songs")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", matchesPattern(LOCATION_HEADER_WITH_ID_REGEX));
    }
}
