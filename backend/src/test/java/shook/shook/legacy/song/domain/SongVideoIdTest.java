package shook.shook.legacy.song.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.legacy.song.exception.SongException;

class SongVideoIdTest {

    @DisplayName("노래의 비디오 ID 를 뜻하는 객체를 생성한다.")
    @Test
    void create_success() {
        //given
        //when
        //then
        Assertions.assertDoesNotThrow(() -> new SongVideoId("elevenVideo"));
    }

    @DisplayName("노래 비디오 ID 가 유효하지 않으면 예외를 던진다.")
    @NullSource
    @ParameterizedTest(name = "노래의 비디오 ID 가 \"{0}\" 일 때")
    @ValueSource(strings = {"", " "})
    void create_fail_lessThanOne(final String imageUrl) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new SongVideoId(imageUrl))
            .isInstanceOf(SongException.NullOrEmptyVideoIdException.class);
    }

    @DisplayName("노래 비디오 ID 의 길이가 11자를 넘을 경우 예외를 던진다.")
    @Test
    void create_fail_lengthOver65536() {
        //given
        final String videoId = ".".repeat(12);

        //when
        //then
        assertThatThrownBy(() -> new SongVideoId(videoId))
            .isInstanceOf(SongException.IncorrectVideoIdLengthException.class);
    }
}
