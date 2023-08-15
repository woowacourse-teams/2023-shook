package shook.shook.auth.ui;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.auth.exception.AuthorizationException;

class AuthContextTest {

    @DisplayName("회원의 경우 authContext의 memberId가 0보다 작은 수나 null이 들어오면 에러를 던진다.")
    @NullSource
    @ValueSource(longs = {-1, -2})
    @ParameterizedTest
    void fail_authContext_set_login_member(final Long memberId) {
        //given
        final AuthContext authContext = new AuthContext();

        //when
        //then
        assertThatThrownBy(() -> authContext.setLoginMember(memberId))
            .isInstanceOf(AuthorizationException.AuthContextException.class);
    }

    @DisplayName("회원의 경우 authContext의 memberId가 1이상의 수가 들어오면 설정을 성공한다.")
    @ValueSource(longs = {1, 2, 10000})
    @ParameterizedTest
    void success_authContext_set_login_member(final Long memberId) {
        //given
        final AuthContext authContext = new AuthContext();

        //when
        authContext.setLoginMember(memberId);

        //then
        assertThat(authContext.getMemberId()).isEqualTo(memberId);
        assertThat(authContext.getMemberStatus()).isEqualTo(MemberStatus.MEMBER);
    }

    @DisplayName("authContext의 memberId값을 가져올 때 null이면 에러를 던진다.")
    @Test
    void fail_authContext_memberId_get() {
        //given
        final AuthContext authContext = new AuthContext();

        //when
        //then
        assertThatThrownBy(authContext::getMemberId)
            .isInstanceOf(AuthorizationException.AuthContextException.class);
    }

    @DisplayName("authContext의 memberId 값을 가져올 때 null이 아닌 경우 성공한다.")
    @Test
    void success_authContext_memberId_get() {
        //given
        final AuthContext authContext = new AuthContext();
        authContext.setLoginMember(1L);

        //when
        final long result = authContext.getMemberId();

        //then
        assertThat(result).isEqualTo(1L);
    }

    @DisplayName("비회원의 경우 authContext의 memberId는 0L이고 member의 상태는 ANONYMOUS이다.")
    @Test
    void success_authContext_set_notLogin_member() {
        //given
        final AuthContext authContext = new AuthContext();

        //when
        authContext.setNotLoginMember();

        //then
        assertThat(authContext.getMemberId()).isEqualTo(0L);
        assertThat(authContext.getMemberStatus()).isEqualTo(MemberStatus.ANONYMOUS);
    }

    @DisplayName("authContext의 memberStatus가 anonymous이면 true를 반환한다.")
    @Test
    void return_true_memberStatus_is_anonymous() {
        //given
        final AuthContext authContext = new AuthContext();
        authContext.setNotLoginMember();

        //when
        //then
        assertThat(authContext.isAnonymous()).isTrue();
    }

    @DisplayName("authContext의 memberStatus가 null이면 false를 반환한다.")
    @Test
    void return_false_memberStatus_is_null() {
        //given
        final AuthContext authContext = new AuthContext();

        //when
        //then
        assertThat(authContext.isAnonymous()).isFalse();
    }

    @DisplayName("authContext의 memberStatus가 Member이면 false를 반환한다.")
    @Test
    void return_false_memberStatus_is_member() {
        //given
        final AuthContext authContext = new AuthContext();
        authContext.setLoginMember(1L);

        //when
        //then
        assertThat(authContext.isAnonymous()).isFalse();
    }

    @DisplayName("authContext의 memberStatus의 값을 가져올 때 null인 경우 예외를 던진다.")
    @Test
    void fail_authContext_memberStatus_get() {
        //given
        final AuthContext authContext = new AuthContext();

        //when
        //then
        assertThatThrownBy(authContext::getMemberStatus)
            .isInstanceOf(AuthorizationException.AuthContextException.class);
    }

    @DisplayName("authContext의 memberStatus의 값을 가져올 때 null이 아닌 경우 성공한다.")
    @Test
    void success_authContext_memberStatus_get() {
        //given
        final AuthContext authContext = new AuthContext();
        authContext.setNotLoginMember();

        //when
        final MemberStatus result = authContext.getMemberStatus();

        //then
        assertThat(result).isEqualTo(MemberStatus.ANONYMOUS);
    }
}
