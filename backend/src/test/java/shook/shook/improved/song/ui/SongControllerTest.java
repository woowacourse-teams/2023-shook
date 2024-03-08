package shook.shook.improved.song.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import shook.shook.improved.artist.domain.Artist;
import shook.shook.improved.artist.domain.repository.ArtistRepository;
import shook.shook.improved.song.application.dto.SongRegisterRequest;
import shook.shook.improved.song.domain.repository.SongRepository;
import shook.shook.improved.support.AcceptanceTest;

class SongControllerTest extends AcceptanceTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @DisplayName("노래를 생성한다.")
    @Test
    void create_Song() {
        // given
        final Artist artist1 = new Artist("아티스트1 프로필", "아티스트1");
        final Artist artist2 = new Artist("아티스트2 프로필", "아티스트2");
        final List<Long> savedArtistIds = artistRepository.saveAll(List.of(artist1, artist2)).stream()
            .map(Artist::getId)
            .toList();

        final SongRegisterRequest request = new SongRegisterRequest("노래제목", "노래비디오글자는11자", "노래 앨범 이미지",
                                                                    savedArtistIds, 180, "팝");

        // when
        RestAssured
            .given().body(request).contentType(ContentType.JSON)
            .when().post("/songs")
            .then().statusCode(HttpStatus.CREATED.value());

        // then
        assertThat(songRepository.findAll().size()).isOne();
    }
}
