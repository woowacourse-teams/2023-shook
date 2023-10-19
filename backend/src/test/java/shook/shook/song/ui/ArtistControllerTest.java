package shook.shook.song.ui;

import static org.assertj.core.api.Assertions.assertThat;

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
import shook.shook.song.application.dto.ArtistWithSongSearchResponse;
import shook.shook.song.application.dto.SongSearchResponse;
import shook.shook.song.domain.Artist;
import shook.shook.song.domain.ArtistSynonym;
import shook.shook.song.domain.InMemoryArtistSynonyms;
import shook.shook.song.domain.Synonym;
import shook.shook.song.domain.repository.ArtistRepository;

@SuppressWarnings("NonAsciiCharacters")
@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ArtistControllerTest {

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
    private ArtistRepository artistRepository;

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

    private List<Long> getSongIdsFromResponse(final ArtistWithSongSearchResponse response) {
        return response.getSongs()
            .stream()
            .map(SongSearchResponse::getId)
            .toList();
    }

    @DisplayName("PUT /singers/synonyms 로 요청을 보내는 경우 DB에 저장된 가수, 동의어와 가수를 동기화한다.")
    @Test
    void updateArtistSynonym() {
        // given
        // when, then
        RestAssured.given().log().all()
            .when().log().all()
            .put("/singers/synonyms")
            .then().log().all()
            .statusCode(HttpStatus.OK.value());
    }
}
