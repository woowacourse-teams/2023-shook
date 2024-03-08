package shook.shook.improved.auth.ui.interceptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PathMethodTest {

    @DisplayName("요청 메소드 이름이 인자로 들어오는 경우 요소의 이름과 비교하여 대소문자 구분없이 같은 true를 반환한다.")
    @ValueSource(strings = {"post", "POST", "Post"})
    @ParameterizedTest
    void return_ture_match_name(String requestMethod) {
        //given
        final PathMethod post = PathMethod.POST;

        //when
        final boolean result = post.match(requestMethod);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("요청 메소드 이름이 인자로 들어오는 경우 요소의 이름과 비교하여 대소문자 구분없이 같은 false를 반환한다.")
    @ValueSource(strings = {"post", "POST", "Post"})
    @ParameterizedTest
    void return_false_nonMatch_name(String requestMethod) {
        //given
        final PathMethod get = PathMethod.GET;

        //when
        final boolean result = get.match(requestMethod);

        //then
        assertThat(result).isFalse();
    }
}
