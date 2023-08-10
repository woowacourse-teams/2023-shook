package shook.shook.auth.ui;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.auth.exception.AuthorizationException;

class AuthContextTest {

    @DisplayName("authContext에 0이하의 수나 Null이 들어오면 에러를 던진다.")
    @NullSource
    @ValueSource(longs = {0, -1, -2})
    @ParameterizedTest
    void fail_authContext_set(final Long memberId) {
        //given
        final AuthContext authContext = new AuthContext();

        //when
        //then
        assertThatThrownBy(() -> authContext.setMemberId(memberId))
            .isInstanceOf(AuthorizationException.AuthContextException.class);
    }

    @DisplayName("authContext에 1이상의 수가 들어오면 설정을 성공한다.")
    @ValueSource(longs = {1, 2, 10000})
    @ParameterizedTest
    void success_authContext_set(final Long memberId) {
        //given
        final AuthContext authContext = new AuthContext();

        //when
        //then
        assertDoesNotThrow(() -> authContext.setMemberId(memberId));
    }

    @DisplayName("authContext의 값을 가져올 때 null이면 에러를 던진다.")
    @Test
    void fail_authContext_get() {
        //given
        final AuthContext authContext = new AuthContext();

        //when
        //then
        assertThatThrownBy(authContext::getMemberId)
            .isInstanceOf(AuthorizationException.AuthContextException.class);
    }

    @DisplayName("authContext의 값을 가져올 때 null이 아닌 경우 성공한다.")
    @Test
    void success_authContext_get() {
        //given
        final AuthContext authContext = new AuthContext();
        authContext.setMemberId(1L);

        //when
        final long result = authContext.getMemberId();

        //then
        assertThat(result).isEqualTo(1L);
    }
}
