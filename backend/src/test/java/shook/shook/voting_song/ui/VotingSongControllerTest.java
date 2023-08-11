package shook.shook.song.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import shook.shook.voting_song.application.dto.VotingSongResponse;
import shook.shook.voting_song.domain.VotingSong;
import shook.shook.voting_song.domain.repository.VotingSongRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VotingSongControllerTest {

    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private VotingSongRepository votingSongRepository;

    @DisplayName("노래 정보를 조회시 제목, 가수, 길이, URL, 킬링파트를 담은 응답을 반환한다.")
    @Test
    void showSongById() {
        //given
        final VotingSong song1 = votingSongRepository.save(
            new VotingSong("제목1", "비디오URL", "이미지URL", "가수", 20));
        final VotingSong song2 = votingSongRepository.save(
            new VotingSong("제목2", "비디오URL", "이미지URL", "가수", 20));

        final List<VotingSongResponse> expected = Stream.of(song1, song2)
            .map(VotingSongResponse::from)
            .toList();

        //when
        final List<VotingSongResponse> response = RestAssured.given().log().all()
            .when().log().all()
            .get("/voting-songs")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().jsonPath().getList(".", VotingSongResponse.class);

        //then
        assertAll(
            () -> assertThat(response).hasSize(2),
            () -> assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected)
        );
    }
}
