package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.song.exception.SongException;

class SongImageUrlTest {

    @DisplayName("노래의 이미지 URL 을 뜻하는 객체를 생성한다.")
    @Test
    void create_success() {
        //given
        //when
        //then
        Assertions.assertDoesNotThrow(() -> new SongImageUrl("이미지 URL"));
    }

    @DisplayName("노래 이미지 URL 이 유효하지 않으면 예외를 던진다.")
    @NullSource
    @ParameterizedTest(name = "노래의 이미지 URL 이 \"{0}\" 일 때")
    @ValueSource(strings = {"", " "})
    void create_fail_lessThanOne(final String imageUrl) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new SongImageUrl(imageUrl))
            .isInstanceOf(SongException.NullOrEmptyImageUrlException.class);
    }

    @DisplayName("노래 이미지 URL 의 길이가 65536를 넘을 경우 예외를 던진다.")
    @Test
    void create_fail_lengthOver65536() {
        //given
        final String imageUrl = ".".repeat(65537);

        //when
        //then
        assertThatThrownBy(() -> new SongImageUrl(imageUrl))
            .isInstanceOf(SongException.TooLongImageUrlException.class);
    }
}
