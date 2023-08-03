package shook.shook.song.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SongControllerTest {

    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private SongRepository songRepository;

    @DisplayName("노래 정보를 조회시 제목, 가수, 길이, URL, 킬링파트를 담은 응답을 반환한다.")
    @Test
    void showSongById() {
        //given
        final Song saved = songRepository.save(new Song("제목", "비디오URL", "이미지URL", "가수", 20));

        //when
        final SongResponse response = RestAssured.given().log().all()
            .when().log().all()
            .get("/songs/" + saved.getId())
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().as(SongResponse.class);

        //then
        assertAll(
            () -> assertThat(response.getTitle()).isEqualTo("제목"),
            () -> assertThat(response.getSinger()).isEqualTo("가수"),
            () -> assertThat(response.getVideoLength()).isEqualTo(20),
            () -> assertThat(response.getSongVideoUrl()).isEqualTo("비디오URL"),
            () -> assertThat(response.getKillingParts()).isEmpty()
        );
    }
}
