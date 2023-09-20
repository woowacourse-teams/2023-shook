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
import shook.shook.song.application.InMemorySongsScheduler;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.dto.SongSwipeResponse;
import shook.shook.song.application.killingpart.KillingPartLikeService;
import shook.shook.song.application.killingpart.dto.HighLikedSongResponse;
import shook.shook.song.application.killingpart.dto.KillingPartLikeRequest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SongControllerTest {

    private static final long FIRST_SONG_KILLING_PART_ID_1 = 1L;
    private static final long FIRST_SONG_KILLING_PART_ID_2 = 2L;
    private static final long SECOND_SONG_KILLING_PART_ID_1 = 4L;
    private static final long MEMBER_ID = 1L;

    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private KillingPartLikeService likeService;

    @Autowired
    private InMemorySongsScheduler inMemorySongsScheduler;

    @DisplayName("노래 정보 처음 조회할 때, 가운데 노래를 기준으로 조회한 경우 200 상태코드, 현재 노래,  이전 / 이후 노래 리스트를 반환한다.")
    @Test
    void showSongById() {
        //given
        final Long songId = 2L;
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_1, MEMBER_ID,
            new KillingPartLikeRequest(true));
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_2, MEMBER_ID,
            new KillingPartLikeRequest(true));
        likeService.updateLikeStatus(SECOND_SONG_KILLING_PART_ID_1, MEMBER_ID,
            new KillingPartLikeRequest(true));

        inMemorySongsScheduler.recreateCachedSong();

        //when
        final SongSwipeResponse response = RestAssured.given().log().all()
            .param("memberId", MEMBER_ID)
            .when().log().all()
            .get("/songs/high-liked/{song_id}", songId)
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().as(SongSwipeResponse.class);

        //then
        assertThat(response.getPrevSongs().get(0).getId()).isEqualTo(1L);
        assertThat(response.getCurrentSong().getId()).isEqualTo(songId);
        assertThat(response.getNextSongs().get(0).getId()).isEqualTo(4L);
    }

    @DisplayName("가장 좋아요가 적은 노래 id 로 이전 노래 정보를 조회할 때 200 상태코드, 이전 노래 리스트를 반환한다.")
    @Test
    void showSongsBeforeSongWithId() {
        // given
        final Long songId = 2L;
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_1, MEMBER_ID,
            new KillingPartLikeRequest(true));
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_2, MEMBER_ID,
            new KillingPartLikeRequest(true));

        inMemorySongsScheduler.recreateCachedSong();

        //when
        final List<SongResponse> response = RestAssured.given().log().all()
            .param("memberId", MEMBER_ID)
            .when().log().all()
            .get("/songs/high-liked/{song_id}/prev", songId)
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().jsonPath().getList(".", SongResponse.class);

        // then
        assertThat(response.stream()
            .map(SongResponse::getId).toList())
            .containsExactly(1L, 4L, 3L);
    }

    @DisplayName("가장 좋아요가 많은 노래 id 로 이후 노래 정보를 조회할 때 200 상태코드, 이후 노래 리스트를 반환한다.")
    @Test
    void showSongsAfterSongWithId() {
        // given
        final Long songId = 3L;
        inMemorySongsScheduler.recreateCachedSong();

        //when
        final List<SongResponse> response = RestAssured.given().log().all()
            .param("memberId", MEMBER_ID)
            .when().log().all()
            .get("/songs/high-liked/{song_id}/next", songId)
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().jsonPath().getList(".", SongResponse.class);

        // then
        assertThat(response.stream()
            .map(SongResponse::getId).toList())
            .usingRecursiveComparison()
            .comparingOnlyFields("id")
            .isEqualTo(List.of(2L, 1L));
    }

    @DisplayName("장르를 쿼리 스트링으로 받아 해당 장르의 노래 리스트를 조회할 때 200 상태코드, 장르별 노래 리스트를 반환한다.")
    @Test
    void showSongsByGenre() {
        // given
        final String genre = "DANCE";

        // when
        final List<HighLikedSongResponse> response = RestAssured.given().log().all()
            .queryParam("genre", genre)
            .param("memberId", MEMBER_ID)
            .when().log().all()
            .get("/songs/high-liked")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().jsonPath().getList(".", HighLikedSongResponse.class);

        // then
        assertThat(response.stream()
            .map(HighLikedSongResponse::getId).toList())
            .containsExactly(4L, 3L, 1L);
    }

    @DisplayName("존재하지 않는 장르를 요청한 경우 400 상태코드를 반환한다.")
    @Test
    void showSongsByWrongGenre() {
        // given
        final String genre = "WRONG_GENRE";

        // when
        // then
        RestAssured.given().log().all()
            .queryParam("genre", genre)
            .param("memberId", MEMBER_ID)
            .when().log().all()
            .get("/songs/high-liked")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .body().jsonPath().getString("genre");
    }
}
