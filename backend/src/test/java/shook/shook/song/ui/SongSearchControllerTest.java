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
import shook.shook.song.application.dto.SearchedSongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:/drop-create-song.sql")
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
        final Song song = songRepository.save(
            new Song("Feel My Rhythm", "비디오URL", "image", "RedVelvet", 20));
        songRepository.save(new Song("Birthday", "비디오URL", "image", "레드벨벳", 20));

        //when
        final List<SearchedSongResponse> responses = RestAssured.given().log().all()
            .when().log().all()
            .param("singer", "redvelvet")
            .get("/songs")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .jsonPath()
            .getList(".", SearchedSongResponse.class);

        //then
        final List<SearchedSongResponse> expectedResponses = List.of(
            SearchedSongResponse.from(song)
        );

        assertThat(responses).usingRecursiveComparison()
            .isEqualTo(expectedResponses);
    }

    @DisplayName("노래 제목으로 노래를 검색시 정확하게 일치하는 노래 목록을 담은 응답을 반환한다.")
    @Test
    void searchSongByTitle() {
        //given
        final Song song1 = songRepository.save(
            new Song("이 밤이 지나면", "비디오URL", "image", "먼데이키즈", 20));
        final Song song2 = songRepository.save(new Song("이 밤이 지나면", "비디오URL", "image", "임재범", 20));
        songRepository.save(new Song("이밤이 지나면", "비디오URL", "image", "뉴진스(New Jeans)", 20));
        songRepository.save(new Song("그 밤이 지나면", "비디오URL", "image", "레드벨벳", 20));

        // when
        final List<SearchedSongResponse> responses = RestAssured.given().log().all()
            .when().log().all()
            .param("title", "이 밤이 지나면")
            .get("/songs")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .jsonPath()
            .getList(".", SearchedSongResponse.class);

        //then
        final List<SearchedSongResponse> expectedResponses = List.of(
            SearchedSongResponse.from(song1),
            SearchedSongResponse.from(song2)
        );

        assertThat(responses).usingRecursiveComparison()
            .isEqualTo(expectedResponses);
    }

    @DisplayName("통합 검색으로 노래를 검색 시, 정확히 일치하는 가수와 제목의 노래 목록을 응답으로 반환한다.")
    @Test
    void searchSongByIntegrationSearch() {
        //given
        songRepository.save(new Song("Super Shy", "비디오URL", "image", "르세라핌", 20));
        final Song song = songRepository.save(new Song("Super Shy", "비디오URL", "image", "뉴진스", 20));
        songRepository.save(new Song("ETA", "비디오URL", "image", "뉴진스", 20));

        //when
        final List<SearchedSongResponse> responses = RestAssured.given().log().all()
            .when().log().all()
            .params("singer", "뉴진스", "title", "Super Shy")
            .get("/songs")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .jsonPath()
            .getList(".", SearchedSongResponse.class);

        //then
        final List<SearchedSongResponse> expectedResponses = List.of(
            SearchedSongResponse.from(song)
        );

        assertThat(responses).usingRecursiveComparison()
            .isEqualTo(expectedResponses);
    }

    @DisplayName("통합 검색으로 노래를 검색 시, 제목과 가수가 모두 비어있다면 빈 결과가 반환된다.")
    @Test
    void searchSongByIntegrationSearch_allEmptyInput() {
        //given
        //when
        final List<SearchedSongResponse> responses = RestAssured.given().log().all()
            .when().log().all()
            .get("/songs")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .jsonPath()
            .getList(".", SearchedSongResponse.class);

        //then
        assertThat(responses).isEmpty();
    }
}
