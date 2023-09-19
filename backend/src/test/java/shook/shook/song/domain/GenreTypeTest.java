package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenreTypeTest {

    @DisplayName("입력값에 따른 장르를 찾는다.")
    @Test
    void from() {
        // given
        // when
        final GenreType genreType = GenreType.from("댄스");

        // then
        assertThat(genreType).isEqualTo(GenreType.DANCE);
    }

    @DisplayName("입력값에 맞는 장르가 없다면 ETC 가 반환된다.")
    @Test
    void from_notFound_ETC() {
        // given
        // when
        final GenreType genreType = GenreType.from("하이");

        // then
        assertThat(genreType).isEqualTo(GenreType.ETC);
    }
}
