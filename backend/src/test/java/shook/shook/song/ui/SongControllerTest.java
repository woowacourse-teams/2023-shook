package shook.shook.song.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.auth.application.TokenProvider;
import shook.shook.member_part.application.MemberPartService;
import shook.shook.member_part.application.dto.MemberPartRegisterRequest;
import shook.shook.song.application.InMemorySongsScheduler;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.killingpart.KillingPartLikeService;
import shook.shook.song.application.killingpart.dto.KillingPartLikeRequest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongControllerTest {

    private static final Long MEMBER_ID = 1L;
    private static final Long FIRST_SONG_KILLING_PART_ID_1 = 1L;
    private static final Long FIRST_SONG_KILLING_PART_ID_2 = 2L;
    private static final Long FIRST_SONG_KILLING_PART_ID_3 = 3L;
    private static final String MEMBER_NICKNAME = "nickname";

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private InMemorySongsScheduler inMemorySongsScheduler;

    @Autowired
    private KillingPartLikeService likeService;

    @Autowired
    private MemberPartService memberPartService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("노래 id 와 토큰을 입력 받아 노래 하나 조회 성공 시, 200 상태 코드를 반환한다.")
    @Test
    void findSongById() {
        // given
        final String accessToken = tokenProvider.createAccessToken(MEMBER_ID, MEMBER_NICKNAME);

        final Long songId = 1L;
        inMemorySongsScheduler.recreateCachedSong();
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_1, MEMBER_ID,
                                     new KillingPartLikeRequest(true));
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_1, MEMBER_ID,
                                     new KillingPartLikeRequest(true));
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_2, MEMBER_ID,
                                     new KillingPartLikeRequest(true));
        likeService.updateLikeStatus(FIRST_SONG_KILLING_PART_ID_3, MEMBER_ID,
                                     new KillingPartLikeRequest(true));

        memberPartService.register(songId, MEMBER_ID, new MemberPartRegisterRequest(0, 10));

        // when
        final SongResponse response = RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .when().log().all()
            .get("/songs/{song_id}", songId)
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().as(SongResponse.class);

        // then
        assertThat(response.getKillingParts().get(0).getId()).isEqualTo(1L);
        assertThat(response.getKillingParts().get(1).getId()).isEqualTo(2L);
        assertThat(response.getKillingParts().get(2).getId()).isEqualTo(3L);
        assertThat(response.getMemberPart().getStart()).isEqualTo(0);
        assertThat(response.getMemberPart().getEnd()).isEqualTo(10);
    }
}
