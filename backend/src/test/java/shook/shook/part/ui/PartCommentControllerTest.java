package shook.shook.part.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import shook.shook.part.application.dto.PartCommentRegisterRequest;
import shook.shook.part.application.dto.PartCommentResponse;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartComment;
import shook.shook.part.domain.PartLength;
import shook.shook.part.domain.repository.PartCommentRepository;
import shook.shook.part.domain.repository.PartRepository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PartCommentControllerTest {

    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private PartCommentRepository partCommentRepository;

    @DisplayName("파트에 댓글 등록시 상태코드 201를 반환한다.")
    @Test
    void registerPartReply() {
        //given
        final Song song = songRepository.save(new Song("제목", "비디오URL", "이미지URL", "가수", 30));
        final Part part = partRepository.save(Part.forSave(1, PartLength.SHORT, song));
        final PartCommentRegisterRequest request = new PartCommentRegisterRequest("댓글 내용");

        //when
        //then
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when().log().all()
            .post("/songs/" + song.getId() + "/parts/" + part.getId() + "/comments")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("파트의 모든 댓글을 조회하여 상태코드 200과 함께 응답한다.")
    @Test
    void findPartReplies() {
        //given
        final Song song = songRepository.save(new Song("제목", "비디오URL", "이미지URL", "가수", 30));
        final Part part = partRepository.save(Part.forSave(1, PartLength.SHORT, song));
        partCommentRepository.save(PartComment.forSave(part, "댓글 내용"));

        //when
        final List<PartCommentResponse> response = RestAssured.given().log().all()
            .when().log().all()
            .get("/songs/" + song.getId() + "/parts/" + part.getId() + "/comments")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .jsonPath()
            .getList(".", PartCommentResponse.class);

        //then
        assertThat(response).hasSize(1);
        assertThat(response.get(0).getContent()).isEqualTo("댓글 내용");
    }
}
