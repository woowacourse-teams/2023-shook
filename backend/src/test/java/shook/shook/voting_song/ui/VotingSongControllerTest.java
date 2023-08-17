package shook.shook.voting_song.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import shook.shook.support.AcceptanceTest;
import shook.shook.voting_song.application.dto.VotingSongResponse;
import shook.shook.voting_song.application.dto.VotingSongSwipeResponse;
import shook.shook.voting_song.domain.VotingSong;
import shook.shook.voting_song.domain.repository.VotingSongRepository;

class VotingSongControllerTest extends AcceptanceTest {

    @Autowired
    private VotingSongRepository votingSongRepository;

    @DisplayName("노래 정보를 조회시 제목, 가수, 길이, URL, 킬링파트를 담은 응답을 반환한다.")
    @Test
    void showSongById() {
        //given
        final VotingSong song1 = votingSongRepository.save(
            new VotingSong("제목1", "비디오ID는 11글자", "이미지URL", "가수", 20));
        final VotingSong song2 = votingSongRepository.save(
            new VotingSong("제목2", "비디오ID는 11글자", "이미지URL", "가수", 20));

        final List<VotingSongResponse> expected = Stream.of(song1, song2)
            .map(VotingSongResponse::from)
            .toList();

        //when
        final List<VotingSongResponse> response = RestAssured.given().log().all()
            .when().log().all()
            .get("/voting-songs")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().jsonPath().getList(".", VotingSongResponse.class);

        //then
        assertAll(
            () -> assertThat(response).hasSize(2),
            () -> assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected)
        );
    }

    @DisplayName("특정 노래를 조회할 때, 이전 노래와 다음 노래의 정보를 담은 응답을 반환한다.")
    @Test
    void findById() {
        // given
        final VotingSong beforeSong = votingSongRepository.save(
            new VotingSong("제목1", "비디오ID는 11글자", "이미지URL", "가수", 20));
        final VotingSong standardSong = votingSongRepository.save(
            new VotingSong("제목2", "비디오ID는 11글자", "이미지URL", "가수", 20));
        final VotingSong afterSong = votingSongRepository.save(
            new VotingSong("제목3", "비디오ID는 11글자", "이미지URL", "가수", 20));

        // when
        final VotingSongSwipeResponse response = RestAssured.given().log().all()
            .when().log().all()
            .get("/voting-songs/{voting_song_id}", standardSong.getId())
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().as(VotingSongSwipeResponse.class);

        // then
        final List<VotingSongResponse> expectedBefore = Stream.of(beforeSong)
            .map(VotingSongResponse::from)
            .toList();
        final List<VotingSongResponse> expectedAfter = Stream.of(afterSong)
            .map(VotingSongResponse::from)
            .toList();

        assertAll(
            () -> assertThat(response.getBeforeSongs()).usingRecursiveComparison()
                .isEqualTo(expectedBefore),
            () -> assertThat(response.getCurrentSong()).usingRecursiveComparison()
                .isEqualTo(VotingSongResponse.from(standardSong)),
            () -> assertThat(response.getAfterSongs()).usingRecursiveComparison()
                .isEqualTo(expectedAfter)
        );
    }

    @DisplayName("특정 노래를 조회할 때, 이전 노래와 다음 노래의 정보를 담은 응답을 반환한다. (다음 노래가 비어있을 때)")
    @Test
    void findByIdEmptyAfterSong() {
        // given
        final VotingSong beforeSong = votingSongRepository.save(
            new VotingSong("제목1", "비디오ID는 11글자", "이미지URL", "가수", 20));
        final VotingSong standardSong = votingSongRepository.save(
            new VotingSong("제목2", "비디오ID는 11글자", "이미지URL", "가수", 20));

        // when
        final VotingSongSwipeResponse response = RestAssured.given().log().all()
            .when().log().all()
            .get("/voting-songs/{voting_song_id}", standardSong.getId())
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().as(VotingSongSwipeResponse.class);

        // then
        final List<VotingSongResponse> expectedBefore = Stream.of(beforeSong)
            .map(VotingSongResponse::from)
            .toList();

        assertAll(
            () -> assertThat(response.getBeforeSongs()).usingRecursiveComparison()
                .isEqualTo(expectedBefore),
            () -> assertThat(response.getCurrentSong()).usingRecursiveComparison()
                .isEqualTo(VotingSongResponse.from(standardSong)),
            () -> assertThat(response.getAfterSongs()).isEmpty()
        );
    }

    @DisplayName("특정 노래를 조회할 때, 이전 노래와 다음 노래의 정보를 담은 응답을 반환한다. (이전 노래가 비어있을 때)")
    @Test
    void findByIdEmptyBeforeSong() {
        // given
        final VotingSong standardSong = votingSongRepository.save(
            new VotingSong("제목1", "비디오ID는 11글자", "이미지URL", "가수", 20));
        final VotingSong afterSong = votingSongRepository.save(
            new VotingSong("제목2", "비디오ID는 11글자", "이미지URL", "가수", 20));

        // when
        final VotingSongSwipeResponse response = RestAssured.given().log().all()
            .when().log().all()
            .get("/voting-songs/{voting_song_id}", standardSong.getId())
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body().as(VotingSongSwipeResponse.class);

        // then
        final List<VotingSongResponse> expectedAfter = Stream.of(afterSong)
            .map(VotingSongResponse::from)
            .toList();

        assertAll(
            () -> assertThat(response.getBeforeSongs()).isEmpty(),
            () -> assertThat(response.getCurrentSong()).usingRecursiveComparison()
                .isEqualTo(VotingSongResponse.from(standardSong)),
            () -> assertThat(response.getAfterSongs()).usingRecursiveComparison()
                .isEqualTo(expectedAfter)
        );
    }
}
