package shook.shook.legacy.song.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.artist.exception.ArtistException;

class ProfileImageUrlTest {

    @DisplayName("ProfileImageUrl 을 생성한다.")
    @Test
    void create_success() {
        // given
        // when, then
        assertDoesNotThrow(() -> new ProfileImageUrl("image"));
    }

    @DisplayName("이미지 URL이 비어있으면 예외를 던진다.")
    @NullSource
    @ParameterizedTest(name = "이미지 URL이 \"{0}\" 일 때")
    @ValueSource(strings = {"", " "})
    void create_fail_lessThanOne(final String value) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new ProfileImageUrl(value))
            .isInstanceOf(ArtistException.NullOrEmptyProfileUrlException.class);
    }

    @DisplayName("이미지 URL의 길이가 65_536을 넘을 경우 예외를 던진다.")
    @Test
    void create_fail_lengthOver65_536() {
        //given
        final String name = ".".repeat(65_537);

        //when
        //then
        assertThatThrownBy(() -> new ProfileImageUrl(name))
            .isInstanceOf(ArtistException.TooLongProfileUrlException.class);
    }
}
