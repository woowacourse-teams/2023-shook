package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.song.exception.ArtistException;

class SynonymTest {

    @DisplayName("가수 이름 동의어를 뜻하는 객체를 생성한다.")
    @Test
    void create_success() {
        //given
        //when
        //then
        Assertions.assertDoesNotThrow(() -> new Synonym("동의어"));
    }

    @DisplayName("가수 이름 동의어가 유효하지 않으면 예외를 던진다.")
    @NullSource
    @ParameterizedTest(name = "동의어가 \"{0}\" 일 때")
    @ValueSource(strings = {"", " "})
    void create_fail_lessThanOne(final String synonym) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Synonym(synonym))
            .isInstanceOf(ArtistException.NullOrEmptySynonymException.class);
    }

    @DisplayName("가수 이름 동의어의 길이가 255를 넘을 경우 예외를 던진다.")
    @Test
    void create_fail_lengthOver255() {
        //given
        final String synonym = ".".repeat(256);

        //when
        //then
        assertThatThrownBy(() -> new Synonym(synonym))
            .isInstanceOf(ArtistException.TooLongSynonymException.class);
    }
}
