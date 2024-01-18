package shook.shook.legacy.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.legacy.member.exception.MemberException;

class NicknameTest {

    @DisplayName("올바른 닉네임을 생성한다.")
    @Test
    void create_success() {
        //given
        //when
        //then
        Assertions.assertDoesNotThrow(() -> new Nickname("shook"));
    }

    @DisplayName("닉네임이 유효하지 않으면 예외를 던진다.")
    @NullSource
    @ParameterizedTest(name = "닉네임이 \"{0}\" 일 때")
    @ValueSource(strings = {"", " "})
    void create_fail_lessThanOne(final String nickname) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Nickname(nickname))
            .isInstanceOf(MemberException.NullOrEmptyNicknameException.class);
    }

    @DisplayName("닉네임의 길이가 100자를 넘을 경우 예외를 던진다.")
    @Test
    void create_fail_lengthOver100() {
        //given
        final String nickname = ".".repeat(101);

        //when
        //then
        assertThatThrownBy(() -> new Nickname(nickname))
            .isInstanceOf(MemberException.TooLongNicknameException.class);
    }
}
