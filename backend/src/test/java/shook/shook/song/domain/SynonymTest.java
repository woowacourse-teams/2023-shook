package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
        assertDoesNotThrow(() -> new Synonym("동의어"));
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

    @DisplayName("입력값으로 시작하는 동의어라면 true, 아니면 false 를 반환한다. (대소문자, 공백 제거)")
    @ParameterizedTest
    @CsvSource(value = {"Hi:true", "H i:true", "HiYou:true", "  Hi:true",
        "Hello:false"}, delimiter = ':')
    void startsWithIgnoringCaseAndWhiteSpace(final String value, final boolean isSame) {
        // given
        final String keyword = "hi";
        final Synonym synonym = new Synonym(value);

        // when
        final boolean result = synonym.startsWithIgnoringCaseAndWhiteSpace(keyword);

        // then
        assertThat(result).isEqualTo(isSame);
    }

    @DisplayName("입력값으로 끝나는 동의어라면 true, 아니면 false 를 반환한다. (대소문자, 공백 제거)")
    @ParameterizedTest
    @CsvSource(value = {"HelloHi:true", "H i:true", "  Hi :true", "Hello:false"}, delimiter = ':')
    void endsWithIgnoringCaseAndWhiteSpace(final String value, final boolean isSame) {
        // given
        final String keyword = "hi";
        final Synonym synonym = new Synonym(value);

        // when
        final boolean result = synonym.endsWithIgnoringCaseAndWhiteSpace(keyword);

        // then
        assertThat(result).isEqualTo(isSame);
    }

    @DisplayName("입력값이 비어있다면 false 가 반환된다.")
    @Test
    void ignoringCaseAndWhiteSpace_emptyValue() {
        // given
        final String keyword = " ";
        final Synonym synonym = new Synonym("hi");

        // when
        final boolean result = synonym.endsWithIgnoringCaseAndWhiteSpace(keyword);

        // then
        assertThat(result).isFalse();
    }
}
