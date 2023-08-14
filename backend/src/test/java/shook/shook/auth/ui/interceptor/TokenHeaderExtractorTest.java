package shook.shook.auth.ui.interceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockHttpServletRequest;
import shook.shook.auth.exception.AuthorizationException;

class TokenHeaderExtractorTest {

    @DisplayName("요청 메세지의 authorization header가 올바르면 토큰을 반환한다.")
    @Test
    void success_extract_token() {
        //given
        final String token = "Bearer token";
        final MockHttpServletRequest request = getMockHttpServletRequest(
            token);

        //when
        final String result = TokenHeaderExtractor.extractToken(request);

        //then
        assertThat(result).isEqualTo("token");
    }

    @DisplayName("요청 메세지의 authorization header가 비어있으면 예외를 던진다.")
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void fail_extract_token_empty(final String token) {
        //given
        final MockHttpServletRequest request = getMockHttpServletRequest(token);

        //when
        //then
        assertThatThrownBy(() -> TokenHeaderExtractor.extractToken(request))
            .isInstanceOf(AuthorizationException.AccessTokenNotFoundException.class);
    }

    @DisplayName("요청 메세지의 authorization header가 잘못된 형태를 가지고 있으면 예외를 던진다.")
    @ValueSource(strings = {"shook token", "token", "Bearer token token"})
    @ParameterizedTest
    void fail_extract_token_invalid_format(final String token) {
        //given
        final MockHttpServletRequest request = getMockHttpServletRequest(token);

        //when
        //then
        assertThatThrownBy(() -> TokenHeaderExtractor.extractToken(request))
            .isInstanceOf(AuthorizationException.InvalidAuthorizationHeaderFormatException.class);
    }

    private static MockHttpServletRequest getMockHttpServletRequest(final String token) {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", token);
        return request;
    }
}
