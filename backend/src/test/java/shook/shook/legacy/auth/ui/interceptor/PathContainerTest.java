package shook.shook.legacy.auth.ui.interceptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.auth.ui.interceptor.PathContainer;
import shook.shook.auth.ui.interceptor.PathMethod;

class PathContainerTest {

    @DisplayName("includePathPattern에 포함되지 않은 pattern의 경우 true를 반환한다.")
    @Test
    void not_include_pattern() {
        //given
        final PathContainer pathContainer = new PathContainer();
        pathContainer.includePathPattern("/test", PathMethod.POST);

        //when
        final boolean result = pathContainer.isNotIncludedPath("/prod", "delete");

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("includePathPattern에 포함된 pattern의 경우 false를 반환한다.")
    @Test
    void include_pattern() {
        //given
        final PathContainer pathContainer = new PathContainer();
        pathContainer.includePathPattern("/test", PathMethod.POST);

        //when
        final boolean result = pathContainer.isNotIncludedPath("/test", "post");

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("excludePathPattern에 포함된 pattern의 경우 false를 반환한다.")
    @Test
    void exclude_pattern() {
        //given
        final PathContainer pathContainer = new PathContainer();
        pathContainer.excludePathPattern("/test", PathMethod.POST);

        //when
        final boolean result = pathContainer.isNotIncludedPath("/test", "post");

        //then
        assertThat(result).isTrue();
    }
}
