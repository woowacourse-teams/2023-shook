package shook.shook.part.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import shook.shook.part.application.dto.PartRegisterRequest;
import shook.shook.part.application.dto.PartRegisterResponse;
import shook.shook.part.domain.Part;
import shook.shook.part.domain.PartLength;
import shook.shook.part.domain.Vote;
import shook.shook.part.domain.repository.PartRepository;
import shook.shook.part.domain.repository.VoteRepository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PartControllerTest {

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
    private VoteRepository voteRepository;

    @DisplayName("파트 첫 등록시 201 상태코드, 파트의 순위와 파트의 URL을 담은 응답을 반환한다.")
    @Test
    void registerPart_unique() {
        //given
        final Song song = songRepository.save(new Song("제목", "비디오URL", "이미지URL", "가수", 30));
        final PartRegisterRequest request = new PartRegisterRequest(1, 10);

        //when
        final PartRegisterResponse response = RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when().log().all()
            .post("/songs/" + song.getId() + "/parts")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract().body().as(PartRegisterResponse.class);

        //then
        assertThat(response.getRank()).isOne();
        assertThat(response.getPartVideoUrl()).isEqualTo("비디오URL?start=1&end=11");
    }

    @DisplayName("기존 파트 재등록시 201 상태코드, 파트의 순위와 파트의 URL을 담은 응답을 반환한다.")
    @Test
    void registerPart_exist() {
        //given
        final Song song = songRepository.save(new Song("제목", "비디오URL", "이미지URL", "가수", 30));
        final Part part = partRepository.save(Part.forSave(1, PartLength.SHORT, song));
        final Vote vote = voteRepository.save(Vote.forSave(part));
        song.addPart(part);
        part.vote(vote);

        final PartRegisterRequest request = new PartRegisterRequest(1, 5);

        //when
        final PartRegisterResponse response = RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when().log().all()
            .post("/songs/" + song.getId() + "/parts")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract().body().as(PartRegisterResponse.class);

        //then
        assertThat(response.getRank()).isOne();
        assertThat(response.getPartVideoUrl()).isEqualTo("비디오URL?start=1&end=6");
    }

    @DisplayName("2표가 있는 파트가 있을 때 새로운 파트를 등록시 201 상태코드, 2위, 파트의 URL을 담은 응답을 반환한다.")
    @Test
    void registerPart() {
        //given
        final Song song = songRepository.save(new Song("제목", "비디오URL", "이미지URL", "가수", 30));
        final Part firstPart = partRepository.save(Part.forSave(1, PartLength.SHORT, song));
        song.addPart(firstPart);
        final Vote firstVote = voteRepository.save(Vote.forSave(firstPart));
        final Vote secondVote = voteRepository.save(Vote.forSave(firstPart));
        firstPart.vote(firstVote);
        firstPart.vote(secondVote);

        final PartRegisterRequest request = new PartRegisterRequest(2, 10);

        //when
        final PartRegisterResponse response = RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when().log().all()
            .post("/songs/" + song.getId() + "/parts")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract().body().as(PartRegisterResponse.class);

        //then
        assertThat(response.getRank()).isEqualTo(2);
        assertThat(response.getPartVideoUrl()).isEqualTo("비디오URL?start=2&end=12");
    }
}
