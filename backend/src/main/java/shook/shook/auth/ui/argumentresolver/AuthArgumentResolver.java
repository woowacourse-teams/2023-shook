package shook.shook.auth.ui.argumentresolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shook.shook.auth.ui.AuthContext;

@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthContext authContext;

    public AuthArgumentResolver(final AuthContext authContext) {
        this.authContext = authContext;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authenticated.class);
    }

    @Override
    public Object resolveArgument(
        final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory
    ) {
        return new MemberInfo(authContext.getMemberId(), authContext.getAuthority());
    }
}
