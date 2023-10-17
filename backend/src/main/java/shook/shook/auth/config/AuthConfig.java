package shook.shook.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shook.shook.auth.ui.argumentresolver.AuthArgumentResolver;
import shook.shook.auth.ui.interceptor.LoginCheckerInterceptor;
import shook.shook.auth.ui.interceptor.PathMatcherInterceptor;
import shook.shook.auth.ui.interceptor.PathMethod;
import shook.shook.auth.ui.interceptor.TokenInterceptor;

import java.util.List;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final AuthArgumentResolver authArgumentResolver;
    private final LoginCheckerInterceptor loginCheckerInterceptor;
    private final TokenInterceptor tokenInterceptor;

    public AuthConfig(final AuthArgumentResolver authArgumentResolver,
                      final LoginCheckerInterceptor loginCheckerInterceptor,
                      final TokenInterceptor tokenInterceptor
    ) {
        this.authArgumentResolver = authArgumentResolver;
        this.loginCheckerInterceptor = loginCheckerInterceptor;
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckerInterceptor());
        registry.addInterceptor(tokenInterceptor());
    }

    private HandlerInterceptor loginCheckerInterceptor() {
        return new PathMatcherInterceptor(loginCheckerInterceptor)
                .includePathPattern("/songs/high-liked/**", PathMethod.GET);
    }

    private HandlerInterceptor tokenInterceptor() {
        return new PathMatcherInterceptor(tokenInterceptor)
                .includePathPattern("/my-page/**", PathMethod.GET)
                .includePathPattern("/songs/*/parts/*/likes", PathMethod.PUT)
                .includePathPattern("/voting-songs/*/parts", PathMethod.POST)
                .includePathPattern("/songs/*/parts/*/comments", PathMethod.POST)
                .includePathPattern("/songs/*/member-parts", PathMethod.POST)
                .includePathPattern("/member-parts/*", PathMethod.DELETE)
                .includePathPattern("/members/*", PathMethod.DELETE);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }
}
