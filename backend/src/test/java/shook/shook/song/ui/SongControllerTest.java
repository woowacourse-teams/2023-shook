package shook.shook.song.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.killingpart.KillingPartLikeService;
import shook.shook.song.application.killingpart.dto.KillingPartLikeRequest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SongControllerTest {

    private static final long FIRST_SONG_ID = 1L;
    private static final long FIRST_SONG_KILLING_PART_ID_1 = 1L;
    private static final long FIRST_SONG_KILLING_PART_ID_2 = 2L;
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
    private SongService songService;

    @DisplayName("노래 정보를 조회시 201 상태코드, 제목, 가수, 길이, URL, 킬링파트를 담은 응답을 반환한다.")
    @Test
    void showSongById() {
        //given
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_1, MEMBER_ID,
            new KillingPartLikeRequest(true));
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_2, MEMBER_ID,
            new KillingPartLikeRequest(true));

        //when
        final SongResponse response = RestAssured.given().log().all()
            .param("memberId", MEMBER_ID)
            .when().log().all()
            .get("/songs/{song_id}", FIRST_SONG_ID)
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().as(SongResponse.class);

        //then
        final SongResponse expected = songService.findByIdAndMemberId(FIRST_SONG_ID, MEMBER_ID);

        assertThat(response).usingRecursiveComparison()
            .isEqualTo(expected);
    }
}
