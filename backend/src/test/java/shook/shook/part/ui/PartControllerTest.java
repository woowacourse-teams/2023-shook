package shook.shook.part.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.part.application.dto.PartRegisterRequest;
import shook.shook.part.application.dto.PartRegisterResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
class PartControllerTest {

    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("파트를 등록시 201 상태코드, 파트의 순위와 파트의 URL을 담은 응답을 반환한다.")
    @Test
    void registerPart() {
        //given
        final PartRegisterRequest request = new PartRegisterRequest(1, 10);

        //when
        final PartRegisterResponse response = RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .when().log().all()
            .post("/songs/1/parts")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract().body().as(PartRegisterResponse.class);

        //then
        assertThat(response.getRank()).isOne();
        assertThat(response.getPartUrl())
            .isEqualTo("https://www.youtube.com/embed/NgEaOJ7lRWY?start=1&end=11");
    }
}
