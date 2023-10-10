package shook.shook.my_part.ui;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.auth.application.TokenProvider;
import shook.shook.my_part.application.dto.MemberPartRegisterRequest;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberPartControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private SongRepository songRepository;

    @DisplayName("멤버의 파트를 등록 성공 시 201 상태 코드를 반환한다.")
    @Test
    void registerMemberPart() {
        // given
        final Song song = songRepository.findById(1L).get();
        final MemberPartRegisterRequest request = new MemberPartRegisterRequest(5, 10);
        final String accessToken = tokenProvider.createAccessToken(1L, "nickname");

        // when
        // then
        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .body(request)
            .contentType(ContentType.JSON)
            .when().log().all().post("/songs/{songId}/member-parts", song.getId())
            .then().statusCode(HttpStatus.CREATED.value());
    }
}
