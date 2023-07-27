package shook.shook.song.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import shook.shook.song.application.dto.SearchedSongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SongSearchControllerTest {

    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private SongRepository songRepository;

    @DisplayName("가수 이름으로 노래를 검색시 정확하게 일치하는 가수의 노래 목록을 담은 응답을 반환한다.")
    @Test
    void searchSongBySinger() {
        //given
        final Song saved1 = songRepository.save(new Song("Super Shy", "비디오URL", "뉴진스", 20));
        final Song saved2 = songRepository.save(new Song("ETA", "비디오URL", "뉴진스", 20));
        songRepository.save(new Song("Ditto", "비디오URL", "뉴진스(New Jeans)", 20));
        songRepository.save(new Song("Feel My Rhythm", "비디오URL", "레드벨벳", 20));

        //when
        final List<SearchedSongResponse> responses = RestAssured.given().log().all()
            .when().log().all()
            .param("singer", "뉴진스")
            .get("/songs")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .jsonPath()
            .getList(".", SearchedSongResponse.class);

        //then
        final List<SearchedSongResponse> expectedResponses = Stream.of(saved1, saved2)
            .map(SearchedSongResponse::from)
            .toList();

        assertThat(responses).usingRecursiveComparison()
            .isEqualTo(expectedResponses);
    }
}
