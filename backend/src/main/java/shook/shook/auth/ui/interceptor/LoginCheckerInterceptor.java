package shook.shook.auth.ui.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shook.shook.auth.ui.AuthContext;

@Component
public class LoginCheckerInterceptor implements HandlerInterceptor {

    private final TokenInterceptor tokenInterceptor;
    private final AuthContext authContext;

    public LoginCheckerInterceptor(
        final TokenInterceptor tokenInterceptor,
        final AuthContext authContext
    ) {
        this.tokenInterceptor = tokenInterceptor;
        this.authContext = authContext;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
        final Object handler) throws Exception {
        if (TokenHeaderExtractor.extractToken(request).isEmpty()) {
            authContext.setNotLoginMember();
            return true;
        }
        return tokenInterceptor.preHandle(request, response, handler);
    }
}
