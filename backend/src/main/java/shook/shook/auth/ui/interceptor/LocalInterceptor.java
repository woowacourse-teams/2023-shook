package shook.shook.auth.ui.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shook.shook.auth.ui.AuthContext;

@Profile("local")
@Component
public class LocalInterceptor implements HandlerInterceptor {

    private final AuthContext authContext;

    public LocalInterceptor(final AuthContext authContext) {
        this.authContext = authContext;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
        throws Exception {
        final long memberId = Long.parseLong(request.getHeader("Authorization"));
        authContext.setAuthenticatedMember(memberId);

        return true;
    }
}
