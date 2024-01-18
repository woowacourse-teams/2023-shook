package shook.shook.legacy.part.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.legacy.part.exception.PartException;

class PartLengthTest {

    @DisplayName("초에 해당하는 PartLength 를 반환한다. (유효하지 않은 초일 때 )")
    @ParameterizedTest(name = "초가 {0}일 때 예외를 던진다.")
    @ValueSource(ints = {-1, 0, 4, 16})
    void findBySecond_invalidSecond(final int second) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new PartLength(second))
            .isInstanceOf(PartException.InvalidLengthException.class);
    }

    @DisplayName("시작초를 받아 끝초를 반환한다. (유효한 초일 때)")
    @ParameterizedTest(name = "PartLength가 {0}일 때 시작이 {1}이면 {2}를 반환한다.")
    @CsvSource(value = {"5:1:6", "10:1:11", "15:1:16"}, delimiter = ':')
    void getEndSecond(final int length, final int start, final int expectedEnd) {
        //given
        final PartLength partLength = new PartLength(length);

        //when
        final int endSecond = partLength.getEndSecond(start);

        //then
        assertThat(expectedEnd).isEqualTo(endSecond);
    }
}
