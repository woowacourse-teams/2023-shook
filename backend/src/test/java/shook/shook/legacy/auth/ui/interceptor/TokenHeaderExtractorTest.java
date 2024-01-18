package shook.shook.legacy.auth.ui.interceptor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockHttpServletRequest;

class TokenHeaderExtractorTest {

    @DisplayName("요청 메세지의 authorization header가 올바르면 토큰을 반환한다.")
    @Test
    void success_extract_token() {
        //given
        final String token = "Bearer token";
        final MockHttpServletRequest request = getMockHttpServletRequest(
            token);

        //when
        final String result = TokenHeaderExtractor.extractToken(request).get();

        //then
        assertThat(result).isEqualTo("token");
    }

    @DisplayName("요청 메세지의 authorization header가 비어있으면 빈 값을 반환한다.")
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void extract_token_empty(final String token) {
        //given
        final MockHttpServletRequest request = getMockHttpServletRequest(token);

        //when
        final Optional<String> result = TokenHeaderExtractor.extractToken(request);

        //then
        assertThat(result.isEmpty()).isTrue();

    }

    private static MockHttpServletRequest getMockHttpServletRequest(final String token) {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", token);
        return request;
    }
}
