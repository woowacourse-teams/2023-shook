package shook.shook.part.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.part.exception.PartCommentException;

class PartCommentContentTest {

    @DisplayName("댓글의 내용을 뜻하는 객체를 생성한다.")
    @Test
    void create_success() {
        //given
        //when
        //then
        Assertions.assertDoesNotThrow(() -> new PartCommentContent("댓글 내용"));
    }

    @DisplayName("댓글의 내용이 유효하지 않으면 예외를 던진다.")
    @NullSource
    @ParameterizedTest(name = "댓글의 내용이 \"{0}\" 일 때")
    @ValueSource(strings = {"", " "})
    void create_fail_nullOrEmpty(final String content) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new PartCommentContent(content))
            .isInstanceOf(PartCommentException.NullOrEmptyPartCommentException.class);
    }

    @DisplayName("댓글의 내용의 길이가 200를 넘을 경우 예외를 던진다.")
    @Test
    void create_fail_lengthOver200() {
        //given
        final String content = ".".repeat(201);

        //when
        //then
        assertThatThrownBy(() -> new PartCommentContent(content))
            .isInstanceOf(PartCommentException.TooLongPartCommentException.class);
    }
}
