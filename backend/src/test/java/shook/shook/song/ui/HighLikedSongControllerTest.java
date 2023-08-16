package shook.shook.song.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
import shook.shook.song.application.killingpart.KillingPartLikeService;
import shook.shook.song.application.killingpart.dto.HighLikedSongResponse;
import shook.shook.song.application.killingpart.dto.KillingPartLikeRequest;


@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HighLikedSongControllerTest {

    private static final long FIRST_SONG_ID = 1L;
    private static final long SECOND_SONG_ID = 2L;
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

    @DisplayName("좋아요 많은 순으로 노래 목록 조회 시 200 상태코드, 좋아요순으로 정렬된 노래 목록이 반환된다.")
    @Test
    void showHighLikedSongs() {
        //given
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_1, MEMBER_ID,
            new KillingPartLikeRequest(true));
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_2, MEMBER_ID,
            new KillingPartLikeRequest(true));
        likeService.updateLikeStatus(SECOND_SONG_KILLING_PART_ID_1, MEMBER_ID,
            new KillingPartLikeRequest(true));

        //when
        final List<HighLikedSongResponse> responses = RestAssured.given().log().all()
            .when().log().all()
            .get("/songs/high-liked")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .jsonPath()
            .getList(".", HighLikedSongResponse.class);

        //then
        assertAll(
            () -> assertThat(responses).hasSize(2),
            () -> assertThat(responses.get(0))
                .hasFieldOrPropertyWithValue("id", FIRST_SONG_ID)
                .hasFieldOrPropertyWithValue("totalLikeCount", 2L),
            () -> assertThat(responses.get(1))
                .hasFieldOrPropertyWithValue("id", SECOND_SONG_ID)
                .hasFieldOrPropertyWithValue("totalLikeCount", 1L)
        );
    }
}
