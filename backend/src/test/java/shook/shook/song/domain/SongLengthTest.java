package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.song.exception.SongException.SongLengthLessThanOneException;

class SongLengthTest {

    @DisplayName("노래의 길이를 뜻하는 객체를 생성한다.")
    @Test
    void create_success() {
        //given
        //when
        //then
        Assertions.assertDoesNotThrow(() -> new SongLength(1));
    }

    @DisplayName("노래의 길이가 유효하지 않으면 예외를 던진다.")
    @ParameterizedTest(name = "노래의 길이가 {0}일 때")
    @ValueSource(ints = {-1, 0})
    void create_fail_lessThanOne(final int length) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new SongLength(length))
            .isInstanceOf(SongLengthLessThanOneException.class);
    }
}
