package shook.shook.song.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
import shook.shook.song.application.dto.LikedKillingPartResponse;
import shook.shook.song.application.killingpart.KillingPartLikeService;
import shook.shook.song.application.killingpart.dto.KillingPartLikeRequest;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.SongRepository;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MyPageControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final long SAVED_MEMBER_ID = 1L;
    private static final String SAVED_MEMBER_NICKNAME = "nickname";

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartLikeService killingPartLikeService;

    @DisplayName("멤버가 좋아요한 킬링파트를 최신순으로 정렬하여 반환한다.")
    @Nested
    class GetLikedKillingParts {

        @DisplayName("좋아요한 킬링파트가 존재할 때. ( 취소한 좋아요가 존재할 때 )")
        @Test
        void likedKillingPartExistWithOneDeletedLikeExist() {
            //given
            final String accessToken = tokenProvider.createAccessToken(SAVED_MEMBER_ID,
                SAVED_MEMBER_NICKNAME);

            final Song firstSong = songRepository.findById(1L).get();
            final Song secondSong = songRepository.findById(2L).get();
            final Song thirdSong = songRepository.findById(3L).get();

            final List<KillingPart> firstSongKillingPart = killingPartRepository.findAllBySong(
                firstSong);
            final List<KillingPart> secondSongKillingPart = killingPartRepository.findAllBySong(
                secondSong);
            final List<KillingPart> thirdSongKillingPart = killingPartRepository.findAllBySong(
                thirdSong);

            final KillingPartLikeRequest likeCreateRequest = new KillingPartLikeRequest(true);
            final KillingPartLikeRequest likeDeleteRequest = new KillingPartLikeRequest(false);

            killingPartLikeService.updateLikeStatus(
                firstSongKillingPart.get(0).getId(),
                1L,
                likeCreateRequest
            );
            killingPartLikeService.updateLikeStatus(
                firstSongKillingPart.get(2).getId(),
                1L,
                likeCreateRequest
            );
            killingPartLikeService.updateLikeStatus(
                firstSongKillingPart.get(2).getId(),
                1L,
                likeDeleteRequest
            );
            killingPartLikeService.updateLikeStatus(
                secondSongKillingPart.get(0).getId(),
                1L,
                likeCreateRequest
            );
            killingPartLikeService.updateLikeStatus(
                thirdSongKillingPart.get(0).getId(),
                1L,
                likeCreateRequest
            );

            //when
            //then

            final List<LikedKillingPartResponse> expected = List.of(
                LikedKillingPartResponse.of(firstSong, firstSongKillingPart.get(0)),
                LikedKillingPartResponse.of(secondSong, secondSongKillingPart.get(0)),
                LikedKillingPartResponse.of(thirdSong, thirdSongKillingPart.get(0))
            );

            final List<LikedKillingPartResponse> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + accessToken)
                .contentType(ContentType.JSON)
                .when().log().all()
                .get("/my-page")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", LikedKillingPartResponse.class);

            assertThat(response).usingRecursiveComparison().isEqualTo(expected);
        }

        @DisplayName("좋아요한 킬링파트가 없을 때")
        @Test
        void notExist() {
            //given
            final String accessToken = tokenProvider.createAccessToken(SAVED_MEMBER_ID,
                SAVED_MEMBER_NICKNAME);

            //when
            //then
            final List<LikedKillingPartResponse> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + accessToken)
                .contentType(ContentType.JSON)
                .when().log().all()
                .get("/my-page")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", LikedKillingPartResponse.class);

            assertThat(response).isEmpty();
        }
    }
}
