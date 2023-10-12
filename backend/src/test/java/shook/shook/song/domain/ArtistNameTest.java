package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.song.exception.ArtistException;

class ArtistNameTest {

    @DisplayName("가수 이름을 뜻하는 객체를 생성한다.")
    @Test
    void create_success() {
        //given
        //when
        //then
        Assertions.assertDoesNotThrow(() -> new ArtistName("이름"));
    }

    @DisplayName("가수 이름이 유효하지 않으면 예외를 던진다.")
    @NullSource
    @ParameterizedTest(name = "가수의 이름이 \"{0}\" 일 때")
    @ValueSource(strings = {"", " "})
    void create_fail_lessThanOne(final String name) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new ArtistName(name))
            .isInstanceOf(ArtistException.NullOrEmptyNameException.class);
    }

    @DisplayName("가수 이름의 길이가 50을 넘을 경우 예외를 던진다.")
    @Test
    void create_fail_lengthOver50() {
        //given
        final String name = ".".repeat(51);

        //when
        //then
        assertThatThrownBy(() -> new ArtistName(name))
            .isInstanceOf(ArtistException.TooLongNameException.class);
    }

    @DisplayName("입력값으로 시작하는 가수 이름이라면 true, 아니면 false 를 반환한다. (대소문자, 공백 제거)")
    @ParameterizedTest
    @CsvSource(value = {"Hi:true", "H i:true", "HiYou:true", "  Hi:true",
        "Hello:false"}, delimiter = ':')
    void startsWithIgnoringCaseAndWhiteSpace(final String value, final boolean isSame) {
        // given
        final String keyword = "hi";
        final ArtistName artistName = new ArtistName(value);

        // when
        final boolean result = artistName.startsWithIgnoringCaseAndWhiteSpace(keyword);

        // then
        assertThat(result).isEqualTo(isSame);
    }

    @DisplayName("입력값으로 끝나는 가수 이름이라면 true, 아니면 false 를 반환한다. (대소문자, 공백 제거)")
    @ParameterizedTest
    @CsvSource(value = {"HelloHi:true", "H i:true", "  Hi :true", "Hello:false"}, delimiter = ':')
    void endsWithIgnoringCaseAndWhiteSpace(final String value, final boolean isSame) {
        // given
        final String keyword = "hi";
        final ArtistName artistName = new ArtistName(value);

        // when
        final boolean result = artistName.endsWithIgnoringCaseAndWhiteSpace(keyword);

        // then
        assertThat(result).isEqualTo(isSame);
    }

    @DisplayName("입력값이 비어있다면 false 가 반환된다.")
    @Test
    void ignoringCaseAndWhiteSpace_emptyValue() {
        // given
        final String keyword = " ";
        final ArtistName artistName = new ArtistName("hi");

        // when
        final boolean result = artistName.endsWithIgnoringCaseAndWhiteSpace(keyword);

        // then
        assertThat(result).isFalse();
    }
}
