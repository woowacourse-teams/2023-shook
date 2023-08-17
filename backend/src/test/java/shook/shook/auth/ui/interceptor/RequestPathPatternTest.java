package shook.shook.auth.ui.interceptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.util.AntPathMatcher;

class RequestPathPatternTest {

    @DisplayName("요청온 path와 method가 같으면 true를 반환한다.")
    @Test
    void return_true_match_request() {
        //given
        final RequestPathPattern requestPathPattern = new RequestPathPattern("/test",
            PathMethod.POST);
        final String request = "/test";
        final String method = "Post";

        //when
        final boolean result = requestPathPattern.match(new AntPathMatcher(), request, method);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("요청온 path나 method가 다르면 false를 반환한다.")
    @CsvSource(value = {"/prod,get", "/test,get", "/prod,post"})
    @ParameterizedTest
    void return_false_match_request(final String request, final String method) {
        //given
        final RequestPathPattern requestPathPattern = new RequestPathPattern("/test",
            PathMethod.POST);

        //when
        final boolean result = requestPathPattern.match(new AntPathMatcher(), request, method);

        //then
        assertThat(result).isFalse();
    }
}
