package shook.shook.song.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.song.application.killingpart.dto.KillingPartCommentRegisterRequest;
import shook.shook.song.application.killingpart.dto.KillingPartCommentResponse;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartComment;
import shook.shook.song.domain.killingpart.repository.KillingPartCommentRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class KillingPartCommentControllerTest {

    private static final long SAVED_KILLING_PART_ID = 1L;
    private static final long SAVED_SONG_ID = 1L;

    @LocalServerPort
    public int port;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartCommentRepository killingPartCommentRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    // TODO: 2023/08/15 memberInfo 추가하고 테스트 다시 작성하기
    @Disabled
    @DisplayName("킬링파트에 댓글 등록시 상태코드 201를 반환한다.")
    @Test
    void registerKillingPartComment() {
        // given
        final KillingPartCommentRegisterRequest request = new KillingPartCommentRegisterRequest(
            "댓글 내용");

        // when, then
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when().log().all()
            .post("/songs/{songId}/parts/{killingPartId}/comments", SAVED_SONG_ID,
                SAVED_KILLING_PART_ID)
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("킬링파트의 모든 댓글을 최신순으로 조회하여 상태코드 200과 함께 응답한다.")
    @Test
    void findKillingPartComments() {
        //given
        final KillingPart killingPart = killingPartRepository.findById(SAVED_KILLING_PART_ID).get();
        final KillingPartComment savedComment1 = killingPartCommentRepository.save(
            KillingPartComment.forSave(killingPart, "댓글 내용"));
        final KillingPartComment savedComment2 = killingPartCommentRepository.save(
            KillingPartComment.forSave(killingPart, "2번 댓글"));

        //when
        final List<KillingPartCommentResponse> response = RestAssured.given().log().all()
            .when().log().all()
            .get("/songs/{songId}/parts/{killingPartId}/comments", SAVED_SONG_ID,
                SAVED_KILLING_PART_ID)
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .jsonPath()
            .getList(".", KillingPartCommentResponse.class);

        //then
        final List<KillingPartCommentResponse> expectedResult = KillingPartCommentResponse.ofComments(
            List.of(savedComment2, savedComment1));

        assertThat(response).usingRecursiveComparison()
            .isEqualTo(expectedResult);
    }
}
