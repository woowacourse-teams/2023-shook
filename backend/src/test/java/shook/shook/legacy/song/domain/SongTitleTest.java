package shook.shook.legacy.song.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.legacy.song.exception.SongException;

class SongTitleTest {

    @DisplayName("노래의 제목을 뜻하는 객체를 생성한다.")
    @Test
    void create_success() {
        //given
        //when
        //then
        Assertions.assertDoesNotThrow(() -> new SongTitle("제목"));
    }

    @DisplayName("노래 제목이 유효하지 않으면 예외를 던진다.")
    @NullSource
    @ParameterizedTest(name = "노래의 제목이 \"{0}\" 일 때")
    @ValueSource(strings = {"", " "})
    void create_fail_lessThanOne(final String title) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new SongTitle(title))
            .isInstanceOf(SongException.NullOrEmptyTitleException.class);
    }

    @DisplayName("노래 제목의 길이가 100을 넘을 경우 예외를 던진다.")
    @Test
    void create_fail_lengthOver100() {
        //given
        final String title = ".".repeat(101);

        //when
        //then
        assertThatThrownBy(() -> new SongTitle(title))
            .isInstanceOf(SongException.TooLongTitleException.class);
    }
}
