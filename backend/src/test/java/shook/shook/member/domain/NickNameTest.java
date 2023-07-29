package shook.shook.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.member.exception.MemberException;

class NickNameTest {

    @DisplayName("올바른 닉네임을 생성한다.")
    @Test
    void create_success() {
        //given
        //when
        //then
        Assertions.assertDoesNotThrow(() -> new NickName("shook"));
    }

    @DisplayName("닉네임이 유효하지 않으면 예외를 던진다.")
    @NullSource
    @ParameterizedTest(name = "닉네임이 \"{0}\" 일 때")
    @ValueSource(strings = {"", " "})
    void create_fail_lessThanOne(final String nickName) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new NickName(nickName))
            .isInstanceOf(MemberException.NullOrEmptyNickNameException.class);
    }

    @DisplayName("닉네ㅜ의 길이가 100자를 넘을 경우 예외를 던진다.")
    @Test
    void create_fail_lengthOver100() {
        //given
        final String nickName = ".".repeat(101);

        //when
        //then
        assertThatThrownBy(() -> new NickName(nickName))
            .isInstanceOf(MemberException.TooLongNickNameException.class);
    }
}
