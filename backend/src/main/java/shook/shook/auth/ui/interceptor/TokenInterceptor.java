package shook.shook.auth.ui.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shook.shook.auth.application.TokenProvider;
import shook.shook.auth.exception.AuthorizationException;
import shook.shook.auth.ui.AuthContext;
import shook.shook.member.application.MemberService;
import shook.shook.member.domain.Nickname;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;
    private final AuthContext authContext;
    private final MemberService memberService;

    public TokenInterceptor(
        final TokenProvider tokenProvider,
        final AuthContext authContext,
        final MemberService memberService
    ) {
        this.tokenProvider = tokenProvider;
        this.authContext = authContext;
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) throws Exception {
        final String token = TokenHeaderExtractor.extractToken(request)
            .orElseThrow(AuthorizationException.AccessTokenNotFoundException::new);
        final Claims claims = tokenProvider.parseClaims(token);
        final Long memberId = claims.get("memberId", Long.class);
        final String nickname = claims.get("nickname", String.class);
        memberService.findByIdAndNickname(memberId, new Nickname(nickname));

        authContext.setAuthenticatedMember(memberId);

        return true;
    }
}
