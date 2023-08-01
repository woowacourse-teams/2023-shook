package shook.shook.song.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.part.application.PartService;
import shook.shook.part.application.dto.PartRegisterRequest;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.HighVotedSongResponse;
import shook.shook.song.application.dto.SongResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:/test.sql")
class HighVotedSongControllerTest {

    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private PartService partService;

    @Autowired
    private SongService songService;

    @DisplayName("총 득표 수가 높은 순으로 노래를 반환한다. ( 득표 수가 같을 경우 최신 등록 순 )")
    @Test
    void showHighVotedSong() {
        //given
        partService.register(1L, new PartRegisterRequest(1, 10));
        partService.register(1L, new PartRegisterRequest(1, 10));

        partService.register(2L, new PartRegisterRequest(1, 10));

        //when
        final List<HighVotedSongResponse> responses = RestAssured.given().log().all()
            .when().log().all()
            .get("/songs/high-voted")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .jsonPath()
            .getList(".", HighVotedSongResponse.class);

        //then
        final SongResponse song1Response = songService.findById(1L);
        final SongResponse song2Response = songService.findById(2L);

        assertThat(responses).hasSize(40);
        assertThat(responses.get(0).getTitle()).isEqualTo(song1Response.getTitle());
        assertThat(responses.get(1).getTitle()).isEqualTo(song2Response.getTitle());
    }
}
