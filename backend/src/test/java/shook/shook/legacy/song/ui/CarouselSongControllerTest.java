package shook.shook.legacy.song.ui;

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
import shook.shook.legacy.song.application.dto.RecentSongCarouselResponse;
import shook.shook.legacy.song.domain.Artist;
import shook.shook.legacy.song.domain.Genre;
import shook.shook.legacy.song.domain.KillingParts;
import shook.shook.legacy.song.domain.Song;
import shook.shook.legacy.song.domain.killingpart.KillingPart;
import shook.shook.legacy.song.domain.repository.ArtistRepository;
import shook.shook.legacy.song.domain.repository.SongRepository;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CarouselSongControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("캐러셀에 보여질 노래들을 조회하면 200 상태코드와 id 높은 순 노래 데이터가 반환된다.")
    @Test
    void findRecentSongsForCarousel() {
        // given
        songRepository.findById(3L).get();
        songRepository.findById(4L).get();
        songRepository.save(createNewSongWithKillingParts());
        songRepository.save(createNewSongWithKillingParts());

        // when
        final List<RecentSongCarouselResponse> response = RestAssured.given().log().all()
            .param("size", 4)
            .when().log().all()
            .get("/songs/recent")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().jsonPath().getList(".", RecentSongCarouselResponse.class);

        // then
        assertThat(response).hasSize(4);
        assertThat(response.stream()
            .map(RecentSongCarouselResponse::getId)
            .toList())
            .containsExactly(6L, 5L, 4L, 3L);
    }

    @DisplayName("캐러셀에 보여질 노래들을 조회할 때, size 파라미터가 전달되지 않으면 기본값인 5개가 조회된다.")
    @Test
    void findRecentSongsForCarousel_noParam() {
        // given
        songRepository.findById(3L).get();
        songRepository.findById(4L).get();
        songRepository.save(createNewSongWithKillingParts());
        songRepository.save(createNewSongWithKillingParts());
        songRepository.save(createNewSongWithKillingParts());

        // when
        final List<RecentSongCarouselResponse> response = RestAssured.given().log().all()
            .when().log().all()
            .get("/songs/recent")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().jsonPath().getList(".", RecentSongCarouselResponse.class);

        // then
        assertThat(response).hasSize(5);
        assertThat(response.stream()
            .map(RecentSongCarouselResponse::getId)
            .toList())
            .containsExactly(7L, 6L, 5L, 4L, 3L);
    }

    private Song createNewSongWithKillingParts() {
        final KillingPart firstKillingPart = KillingPart.forSave(10, 5);
        final KillingPart secondKillingPart = KillingPart.forSave(15, 5);
        final KillingPart thirdKillingPart = KillingPart.forSave(20, 5);

        final Artist artist = new Artist("image", "name");
        artistRepository.save(artist);
        return new Song(
            "제목", "비디오ID는 11글자", "이미지URL", artist, 5, Genre.from("댄스"),
            new KillingParts(List.of(firstKillingPart, secondKillingPart, thirdKillingPart)));
    }
}
