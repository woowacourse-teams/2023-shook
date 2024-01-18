package shook.shook.auth.ui.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginCheckerInterceptor implements HandlerInterceptor {

    private final TokenInterceptor tokenInterceptor;

    public LoginCheckerInterceptor(
        final TokenInterceptor tokenInterceptor
    ) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        if (TokenHeaderExtractor.extractToken(request).isEmpty()) {
            return true;
        }
        return tokenInterceptor.preHandle(request, response, handler);
    }
}
