package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.song.exception.SongException;

class SingerTest {

    @DisplayName("가수을 뜻하는 객체를 생성한다.")
    @Test
    void create_success() {
        //given
        //when
        //then
        Assertions.assertDoesNotThrow(() -> new Singer("이름"));
    }

    @DisplayName("가수 이름이 유효하지 않으면 예외를 던진다.")
    @NullSource
    @ParameterizedTest(name = "가수의 이름이 \"{0}\" 일 때")
    @ValueSource(strings = {"", " "})
    void create_fail_lessThanOne(final String name) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Singer(name))
            .isInstanceOf(SongException.NullOrEmptySingerNameException.class);
    }

    @DisplayName("가수 이름의 길이가 100을 넘을 경우 예외를 던진다.")
    @Test
    void create_fail_lengthOver100() {
        //given
        final String name = ".".repeat(101);

        //when
        //then
        assertThatThrownBy(() -> new Singer(name))
            .isInstanceOf(SongException.TooLongSingerNameException.class);
    }
}
