package shook.shook.improved.auth.ui;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AuthContextTest {

    @DisplayName("회원의 경우 authContext의 memberId가 1이상의 수가 들어오면 설정을 성공한다.")
    @ValueSource(longs = {1, 2, 10000})
    @ParameterizedTest
    void success_authContext_set_login_member(final Long memberId) {
        //given
        final AuthContext authContext = new AuthContext();

        //when
        authContext.setAuthenticatedMember(memberId);

        //then
        assertThat(authContext.getMemberId()).isEqualTo(memberId);
        assertThat(authContext.getAuthority()).isEqualTo(Authority.MEMBER);
    }

    @DisplayName("authContext의 memberId 값을 가져올 때 null이 아닌 경우 성공한다.")
    @Test
    void success_authContext_memberId_get() {
        //given
        final AuthContext authContext = new AuthContext();
        authContext.setAuthenticatedMember(1L);

        //when
        final long result = authContext.getMemberId();

        //then
        assertThat(result).isEqualTo(1L);
    }

    @DisplayName("authContext의 memberStatus가 Member이면 비회원검사에서 false를 반환한다.")
    @Test
    void return_false_memberStatus_is_member() {
        //given
        final AuthContext authContext = new AuthContext();
        authContext.setAuthenticatedMember(1L);

        //when
        //then
        assertThat(authContext.isAnonymous()).isFalse();
    }
}
