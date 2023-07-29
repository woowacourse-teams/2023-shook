package shook.shook.song.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import shook.shook.song.application.dto.UnregisteredSongSearchResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ManiaDBApiControllerTest {

    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("내부 DB에 등록되지 않은 노래를 검색할 때 200 상태코드, 노래의 이름과 가수, 앨범 커버를 담은 응답을 반환한다.")
    @Test
    void registerPart_unique() {
        //given
        final String keyword = "흔들리는꽃들속에서네샴푸향이느껴진거야";
        final UnregisteredSongSearchResponse expectedFirstResponse = new UnregisteredSongSearchResponse(
            "흔들리는 꽃들 속에서 네 샴푸향이 느껴진거야",
            "피아노 가이",
            "http://i.maniadb.com/images/album/908/908953_1_f.jpg"
        );

        //when
        final UnregisteredSongSearchResponse[] songResponses = RestAssured.given().log().all()
            .when().log().all()
            .get("/songs/unregistered/search?keyword=" + keyword)
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().body().as(UnregisteredSongSearchResponse[].class);

        final List<UnregisteredSongSearchResponse> responses = Arrays.stream(
            songResponses).toList();

        //then
        assertAll(
            () -> assertThat(responses).hasSize(19),
            () -> assertThat(responses.get(0)).usingRecursiveComparison()
                .isEqualTo(expectedFirstResponse)
        );
    }
}
