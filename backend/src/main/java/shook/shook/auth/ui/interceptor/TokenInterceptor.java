package shook.shook.auth.ui.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shook.shook.auth.application.TokenProvider;
import shook.shook.auth.ui.AuthContext;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;
    private final AuthContext authContext;

    public TokenInterceptor(
        final TokenProvider tokenProvider,
        final AuthContext authContext
    ) {
        this.tokenProvider = tokenProvider;
        this.authContext = authContext;
    }

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) {
        final String token = TokenHeaderExtractor.extractToken(request);
        final Claims claims = tokenProvider.parseClaims(token);
        final Long memberId = claims.get("memberId", Long.class);
        authContext.setMemberId(memberId);

        return true;
    }
}
