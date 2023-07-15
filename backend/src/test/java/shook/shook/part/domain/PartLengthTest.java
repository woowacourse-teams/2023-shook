package shook.shook.part.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.part.exception.PartException;

class PartLengthTest {

    @DisplayName("초에 해당하는 PartLength 를 반환한다. (유효한 초일 때)")
    @ParameterizedTest(name = "초가 {0}일 때 {1}를 반환한다.")
    @CsvSource(value = {"5:SHORT", "10:STANDARD", "15:LONG"}, delimiter = ':')
    void findBySecond_exist(final int second, final PartLength partLength) {
        //given
        //when
        final PartLength expected = PartLength.findBySecond(second);

        //then
        assertThat(expected).isEqualTo(partLength);
        assertThat(expected.getValue()).isEqualTo(second);
    }

    @DisplayName("초에 해당하는 PartLength 를 반환한다. (유효하지 않은 초일 때 )")
    @ParameterizedTest(name = "초가 {0}일 때 예외를 던진다.")
    @ValueSource(ints = {-1, 0, 4, 6, 9, 11, 14, 16})
    void findBySecond_invalidSecond(final int second) {
        //given
        //when
        //then
        assertThatThrownBy(() -> PartLength.findBySecond(second))
            .isInstanceOf(PartException.InvalidLengthException.class);
    }

    @DisplayName("시작초를 받아 끝초를 반환한다. (유효한 초일 때)")
    @ParameterizedTest(name = "PartLength가 {0}일 때 시작이 {1}이면 {2}를 반환한다.")
    @CsvSource(value = {"SHORT:1:6", "STANDARD:1:11", "LONG:1:16"}, delimiter = ':')
    void getEndSecond(final PartLength partLength, final int start, final int expectedEnd) {
        //given
        //when
        final int endSecond = partLength.getEndSecond(start);
        //then
        assertThat(expectedEnd).isEqualTo(endSecond);
    }
}
