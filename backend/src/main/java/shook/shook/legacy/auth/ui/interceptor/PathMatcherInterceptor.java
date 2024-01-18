package shook.shook.legacy.auth.ui.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class PathMatcherInterceptor implements HandlerInterceptor {

    private final HandlerInterceptor handlerInterceptor;
    private final PathContainer pathContainer;

    public PathMatcherInterceptor(final HandlerInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor;
        this.pathContainer = new PathContainer();
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        if (pathContainer.isNotIncludedPath(request.getServletPath(), request.getMethod())) {
            return true;
        }
        return handlerInterceptor.preHandle(request, response, handler);
    }

    public PathMatcherInterceptor includePathPattern(final String requestPathPattern,
                                                     final PathMethod requestPathMethod) {
        pathContainer.includePathPattern(requestPathPattern, requestPathMethod);
        return this;
    }

    public PathMatcherInterceptor excludePathPattern(final String requestPathPattern,
                                                     final PathMethod requestPathMethod) {
        pathContainer.excludePathPattern(requestPathPattern, requestPathMethod);
        return this;
    }
}
