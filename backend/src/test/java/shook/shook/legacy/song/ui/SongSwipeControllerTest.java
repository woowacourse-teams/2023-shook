package shook.shook.legacy.song.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.auth.application.TokenProvider;
import shook.shook.legacy.member_part.application.MemberPartService;
import shook.shook.legacy.member_part.application.dto.MemberPartRegisterRequest;
import shook.shook.legacy.song.application.InMemorySongsScheduler;
import shook.shook.legacy.song.application.dto.SongResponse;
import shook.shook.legacy.song.application.dto.SongSwipeResponse;
import shook.shook.legacy.song.application.killingpart.KillingPartLikeService;
import shook.shook.legacy.song.application.killingpart.dto.HighLikedSongResponse;
import shook.shook.legacy.song.application.killingpart.dto.KillingPartLikeRequest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SongSwipeControllerTest {

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
    private TokenProvider tokenProvider;

    @Autowired
    private KillingPartLikeService likeService;

    @Autowired
    private MemberPartService memberPartService;

    @Autowired
    private InMemorySongsScheduler inMemorySongsScheduler;

    @DisplayName("노래 정보 처음 조회할 때, 가운데 노래를 기준으로 조회한 경우 200 상태코드, 현재 노래,  이전 / 이후 노래 리스트를 반환한다.")
    @Test
    void showSongById() {
        //given
        final String accessToken = tokenProvider.createAccessToken(MEMBER_ID, "nickname");

        final Long songId = 2L;
        inMemorySongsScheduler.recreateCachedSong();
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_1, MEMBER_ID,
                                     new KillingPartLikeRequest(true));
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_2, MEMBER_ID,
                                     new KillingPartLikeRequest(true));
        likeService.updateLikeStatus(SECOND_SONG_KILLING_PART_ID_1, MEMBER_ID,
                                     new KillingPartLikeRequest(true));

        // 정렬 순서: 1L, 2L, 4L, 3L
        //when
        final SongSwipeResponse response = RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .when().log().all()
            .get("/songs/high-liked/{song_id}", songId)
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().as(SongSwipeResponse.class);

        //then
        assertThat(response.getPrevSongs().stream()
                       .map(SongResponse::getId).toList())
            .containsExactly(1L);
        assertThat(response.getCurrentSong().getId()).isEqualTo(songId);
        assertThat(response.getNextSongs().stream()
                       .map(SongResponse::getId).toList())
            .containsExactly(4L, 3L);
    }

    @DisplayName("가장 좋아요가 적은 노래 id 로 이전 노래 정보를 조회할 때 200 상태코드, 이전 노래 리스트를 반환한다.")
    @Test
    void showSongsBeforeSongWithId() {
        // given
        final Long songId = 2L;
        final String accessToken = tokenProvider.createAccessToken(MEMBER_ID, "nickname");

        inMemorySongsScheduler.recreateCachedSong();
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_1, MEMBER_ID,
                                     new KillingPartLikeRequest(true));
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_2, MEMBER_ID,
                                     new KillingPartLikeRequest(true));

        memberPartService.register(1L, MEMBER_ID, new MemberPartRegisterRequest(5, 5));

        // 정렬 순서 1L, 4L, 3L, 2L
        //when
        final List<SongResponse> response = RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
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
        assertThat(response.get(0).getMemberPart().getId()).isNotNull();
        assertThat(response.get(1).getMemberPart()).isNull();
        assertThat(response.get(2).getMemberPart()).isNull();
    }

    @DisplayName("가장 좋아요가 많은 노래 id 로 이후 노래 정보를 조회할 때 200 상태코드, 이후 노래 리스트를 반환한다.")
    @Test
    void showSongsAfterSongWithId() {
        // given
        final Long songId = 3L;
        final String accessToken = tokenProvider.createAccessToken(MEMBER_ID, "nickname");
        memberPartService.register(1L, MEMBER_ID, new MemberPartRegisterRequest(5, 5));

        // 정렬 순서 3L, 2L, 1L
        inMemorySongsScheduler.recreateCachedSong();

        //when
        final List<SongResponse> response = RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .when().log().all()
            .get("/songs/high-liked/{song_id}/next", songId)
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().jsonPath().getList(".", SongResponse.class);

        // then
        assertThat(response.stream()
                       .map(SongResponse::getId).toList())
            .containsExactly(2L, 1L);
        assertThat(response.get(0).getMemberPart()).isNull();
        assertThat(response.get(1).getMemberPart()).isNotNull();
    }

    @DisplayName("장르 스와이프를 요청할 때, 장르별 노래 리스트를 조회할 수 있다.")
    @Nested
    class GenreSwipe {

        @DisplayName("장르를 쿼리 스트링으로 받아 해당 장르의 노래 리스트를 조회할 때 200 상태코드, 장르별 노래 리스트를 반환한다.")
        @Test
        void showSongsByGenre() {
            // given
            final String accessToken = tokenProvider.createAccessToken(MEMBER_ID, "nickname");
            final String genre = "DANCE";

            // 정렬 순서 4L, 3L, 1L
            inMemorySongsScheduler.recreateCachedSong();

            // when
            final List<HighLikedSongResponse> response = RestAssured.given().log().all()
                .queryParam("genre", genre)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
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
            inMemorySongsScheduler.recreateCachedSong();

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

        @DisplayName("비회원이 장르 노래 정보를 처음으로 조회할 때, 가운데 장르 노래를 기준으로 조회한 경우 200 상태코드, 현재 노래, 이전 / 이후 장르 노래 리스트를 반환한다.")
        @Test
        void findSongsByGenreForSwipe() {
            //given
            final Long songId = 4L;
            final String genre = "DANCE";
            final String accessToken = tokenProvider.createAccessToken(MEMBER_ID, "nickname");

            inMemorySongsScheduler.recreateCachedSong();
            likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_1, MEMBER_ID,
                                         new KillingPartLikeRequest(true));
            likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_2, MEMBER_ID,
                                         new KillingPartLikeRequest(true));
            likeService.updateLikeStatus(SECOND_SONG_KILLING_PART_ID_1, MEMBER_ID,
                                         new KillingPartLikeRequest(true));

            memberPartService.register(1L, MEMBER_ID, new MemberPartRegisterRequest(5, 5));

            // 정렬 순서 1L, 4L, 3L
            //when
            final SongSwipeResponse response = RestAssured.given().log().all()
                .queryParam("genre", genre)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when().log().all()
                .get("/songs/high-liked/{songId}", songId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body().as(SongSwipeResponse.class);

            //then
            assertThat(response.getPrevSongs().stream()
                           .map(SongResponse::getId).toList())
                .containsExactly(1L);
            assertThat(response.getCurrentSong().getId()).isEqualTo(songId);
            assertThat(response.getNextSongs().stream()
                           .map(SongResponse::getId).toList())
                .containsExactly(3L);
            assertThat(response.getPrevSongs().get(0).getMemberPart()).isNotNull();
            assertThat(response.getCurrentSong().getMemberPart()).isNull();
            assertThat(response.getNextSongs().get(0).getMemberPart()).isNull();
        }

        @DisplayName("가장 좋아요가 적은 장르 노래 id 로 이전 장르 노래 정보를 조회할 때 200 상태코드, 이전 장르 노래 리스트를 반환한다.")
        @Test
        void showPrevSongsWithGenre() {
            //given
            final Long songId = 3L;
            final String genre = "DANCE";
            final String accessToken = tokenProvider.createAccessToken(MEMBER_ID, "nickname");

            inMemorySongsScheduler.recreateCachedSong();
            likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_1, MEMBER_ID,
                                         new KillingPartLikeRequest(true));
            likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_2, MEMBER_ID,
                                         new KillingPartLikeRequest(true));

            memberPartService.register(1L, MEMBER_ID, new MemberPartRegisterRequest(5, 5));

            // 정렬 순서 1L, 4L, 3L
            //when
            final List<SongResponse> response = RestAssured.given().log().all()
                .queryParam("genre", genre)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when().log().all()
                .get("/songs/high-liked/{songId}/prev", songId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body().jsonPath().getList(".", SongResponse.class);

            //then
            assertThat(response.stream()
                           .map(SongResponse::getId).toList())
                .containsExactly(1L, 4L);
            assertThat(response.get(0).getMemberPart()).isNotNull();
            assertThat(response.get(1).getMemberPart()).isNull();
        }

        @DisplayName("가장 좋아요가 많은 장르 노래 id 로 이후 장르 노래 정보를 조회할 때 200 상태코드, 이후 장르 노래 리스트를 반환한다.")
        @Test
        void showNextSongsWithGenre() {
            //given
            final Long songId = 1L;
            final String genre = "DANCE";
            final String accessToken = tokenProvider.createAccessToken(MEMBER_ID, "nickname");

            inMemorySongsScheduler.recreateCachedSong();
            likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_1, MEMBER_ID,
                                         new KillingPartLikeRequest(true));
            likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_2, MEMBER_ID,
                                         new KillingPartLikeRequest(true));
            likeService.updateLikeStatus(SECOND_SONG_KILLING_PART_ID_1, MEMBER_ID,
                                         new KillingPartLikeRequest(true));

            memberPartService.register(4L, MEMBER_ID, new MemberPartRegisterRequest(5, 5));

            // 정렬 순서 1L, 4L, 3L
            //when
            final List<SongResponse> response = RestAssured.given().log().all()
                .queryParam("genre", genre)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when().log().all()
                .get("/songs/high-liked/{songId}/next", songId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body().jsonPath().getList(".", SongResponse.class);

            //then
            assertThat(response.stream()
                           .map(SongResponse::getId).toList())
                .containsExactly(4L, 3L);
            assertThat(response.get(0).getMemberPart()).isNotNull();
            assertThat(response.get(1).getMemberPart()).isNull();
        }
    }
}
