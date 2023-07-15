package shook.shook.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class StringCheckerTest {

    @DisplayName("문자열이 null 이거나 비어있는 경우")
    @NullAndEmptySource
    @ParameterizedTest(name = "문자열이 {0} 일 때")
    void isNullOrBlank(final String string) {
        //given
        //when
        final boolean nullOrBlank = StringChecker.isNullOrBlank(string);

        //then
        assertThat(nullOrBlank).isTrue();
    }

    @DisplayName("문자열이 null 이거나 비어있는 경우")
    @Test
    void isNotNullOrBlank() {
        //given
        //when
        final boolean nullOrBlank = StringChecker.isNullOrBlank("exist");

        //then
        assertThat(nullOrBlank).isFalse();
    }
}
