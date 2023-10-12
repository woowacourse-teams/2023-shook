package shook.shook.song.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.song.application.dto.ArtistResponse;
import shook.shook.song.application.dto.ArtistWithSongSearchResponse;
import shook.shook.song.application.dto.SongSearchResponse;
import shook.shook.song.domain.Artist;
import shook.shook.song.domain.ArtistSynonym;
import shook.shook.song.domain.InMemoryArtistSynonyms;
import shook.shook.song.domain.Synonym;
import shook.shook.song.domain.repository.ArtistRepository;
import shook.shook.song.domain.repository.ArtistSynonymRepository;

@SuppressWarnings("NonAsciiCharacters")
@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ArtistSongSearchControllerTest {

    private Artist newJeans;
    private Artist 가수;
    private Artist 정국;

    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        newJeans = artistRepository.findById(1L).get();
        가수 = artistRepository.findById(2L).get();
        정국 = artistRepository.findById(3L).get();
        final ArtistSynonym synonym1 = new ArtistSynonym(newJeans, new Synonym("인기뉴진스"));
        final ArtistSynonym synonym2 = new ArtistSynonym(가수, new Synonym("인기가수"));
        final ArtistSynonym synonym3 = new ArtistSynonym(정국, new Synonym("방탄인기"));

        artistSynonyms.initialize(
            Map.of(
                synonym1, newJeans,
                synonym2, 가수,
                synonym3, 정국
            )
        );
    }

    @Autowired
    private InMemoryArtistSynonyms artistSynonyms;

    @Autowired
    private ArtistSynonymRepository synonymRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @DisplayName("search=singer,song name=검색어 으로 요청을 보내는 경우 상태코드 200, 검색어로 시작하거나 끝나는 가수, 가수의 TOP3 노래 리스트를 반환한다.")
    @Test
    void searchArtistWithSongByKeyword() {
        // given
        final String searchType = "singer,song";
        final String keyword = "인기";

        // when
        final List<ArtistWithSongSearchResponse> response = RestAssured.given().log().all()
            .params(Map.of("name", keyword, "search", searchType))
            .when().log().all()
            .get("/singers")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().jsonPath().getList(".", ArtistWithSongSearchResponse.class);

        // then
        assertThat(response).hasSize(3);
        final ArtistWithSongSearchResponse firstResponse = response.get(0);
        assertThat(firstResponse.getId()).isEqualTo(newJeans.getId());
        assertThat(getSongIdsFromResponse(firstResponse)).containsExactly(3L, 1L);

        final ArtistWithSongSearchResponse secondResponse = response.get(1);
        assertThat(secondResponse.getId()).isEqualTo(가수.getId());
        assertThat(getSongIdsFromResponse(secondResponse)).containsExactly(2L);

        final ArtistWithSongSearchResponse thirdResponse = response.get(2);
        assertThat(thirdResponse.getId()).isEqualTo(정국.getId());
        assertThat(getSongIdsFromResponse(thirdResponse)).containsExactly(4L);
    }

    private List<Long> getSongIdsFromResponse(final ArtistWithSongSearchResponse response) {
        return response.getSongs()
            .stream()
            .map(SongSearchResponse::getId)
            .toList();
    }

    @DisplayName("search=singer name=검색어 으로 요청을 보내는 경우 상태코드 200, 검색어로 시작하는 가수 목록을 반환한다.")
    @Test
    void searchArtistByKeyword() {
        // given
        final String searchType = "singer";
        final String keyword = "인기";

        // when
        final List<ArtistResponse> response = RestAssured.given().log().all()
            .params(Map.of("name", keyword, "search", searchType))
            .when().log().all()
            .get("/singers")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().jsonPath().getList(".", ArtistResponse.class);

        // then
        // 가나다 순 정렬, 가수 -> 뉴진스
        assertAll(
            () -> assertThat(response).hasSize(2),
            () -> assertThat(response.get(0)).hasFieldOrPropertyWithValue("id", newJeans.getId()),
            () -> assertThat(response.get(1)).hasFieldOrPropertyWithValue("id", 가수.getId())
        );
    }

    @DisplayName("GET /singers/{singerId} 로 요청을 보내는 경우 상태코드 200, 해당 가수의 정보, 모든 노래 리스트를 반환한다.")
    @Test
    void searchSongsByArtist() {
        // given
        final Long singerId = newJeans.getId();

        // when
        final ArtistWithSongSearchResponse response = RestAssured.given().log().all()
            .when().log().all()
            .get("/singers/{singerId}", singerId)
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().as(ArtistWithSongSearchResponse.class);

        // then
        assertThat(response.getId()).isEqualTo(newJeans.getId());
        assertThat(getSongIdsFromResponse(response)).containsExactly(3L, 1L);
    }
}
