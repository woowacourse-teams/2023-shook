package shook.shook.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import shook.shook.auth.application.OAuthType;
import shook.shook.auth.exception.OAuthException;

class OAuthTypeTest {

    @DisplayName("소셜 로그인의 타입을 찾는다.")
    @MethodSource("requestOauthTypeAndResult")
    @ParameterizedTest
    void findOauthType(final String requestOauthType, final OAuthType expect) {
        // given
        // when
        final OAuthType result = OAuthType.find(requestOauthType);

        // then
        assertThat(result).isEqualTo(expect);
    }

    private static Stream<Arguments> requestOauthTypeAndResult() {
        return Stream.of(
            arguments("google", OAuthType.GOOGLE),
            arguments("kakao", OAuthType.KAKAO)
        );
    }

    @DisplayName("현재 존재하는 소셜 로그인의 타입 이외의 타입의 요청이 들어오면 예외를 발생한다.")
    @ValueSource(strings = "facebook, naver")
    @ParameterizedTest
    void notFoundOauthType(final String requestOauthType) {
        // given
        // when
        // then
        assertThatThrownBy(() -> OAuthType.find(requestOauthType))
            .isInstanceOf(OAuthException.OauthTypeNotFoundException.class);
    }
}
