package shook.shook.song.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenreTest {

    @DisplayName("입력값에 따른 장르를 찾는다.")
    @Test
    void from() {
        // given
        // when
        final Genre genre = Genre.from("댄스");

        // then
        assertThat(genre).isEqualTo(Genre.DANCE);
    }

    @DisplayName("입력값에 맞는 장르가 없다면 ETC 가 반환된다.")
    @Test
    void from_notFound_ETC() {
        // given
        // when
        final Genre genre = Genre.from("하이");

        // then
        assertThat(genre).isEqualTo(Genre.ETC);
    }
}
