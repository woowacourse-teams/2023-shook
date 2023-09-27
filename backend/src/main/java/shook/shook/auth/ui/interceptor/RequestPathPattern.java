package shook.shook.auth.ui.interceptor;

import org.springframework.util.PathMatcher;

public class RequestPathPattern {

    private final String path;
    private final PathMethod method;

    public RequestPathPattern(final String path, final PathMethod method) {
        this.path = path;
        this.method = method;
    }

    public boolean match(final PathMatcher pathMatcher,
                         final String requestPath,
                         final String requestMethod) {
        return pathMatcher.match(path, requestPath) && method.match(requestMethod);
    }
}
