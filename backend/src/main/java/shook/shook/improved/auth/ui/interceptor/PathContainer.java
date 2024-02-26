package shook.shook.improved.auth.ui.interceptor;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class PathContainer {

    private final PathMatcher pathMatcher;
    private final List<RequestPathPattern> includePathPatterns;
    private final List<RequestPathPattern> excludePathPatterns;

    public PathContainer() {
        this.pathMatcher = new AntPathMatcher();
        this.includePathPatterns = new ArrayList<>();
        this.excludePathPatterns = new ArrayList<>();
    }

    public boolean isNotIncludedPath(final String requestPath, final String requestMethod) {
        final boolean isExcludedPattern = excludePathPatterns.stream()
            .anyMatch(pathPattern -> pathPattern.match(pathMatcher, requestPath, requestMethod));

        final boolean isNotIncludedPattern = includePathPatterns.stream()
            .noneMatch(pathPattern -> pathPattern.match(pathMatcher, requestPath, requestMethod));

        return isExcludedPattern || isNotIncludedPattern;
    }

    public void includePathPattern(final String requestPath, final PathMethod requestMethod) {
        this.includePathPatterns.add(new RequestPathPattern(requestPath, requestMethod));
    }

    public void excludePathPattern(final String requestPath, final PathMethod requestMethod) {
        this.excludePathPatterns.add(new RequestPathPattern(requestPath, requestMethod));
    }
}
