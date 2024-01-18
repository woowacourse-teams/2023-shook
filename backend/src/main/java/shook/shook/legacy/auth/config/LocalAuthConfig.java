package shook.shook.legacy.auth.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shook.shook.legacy.auth.ui.argumentresolver.AuthArgumentResolver;
import shook.shook.legacy.auth.ui.interceptor.LocalInterceptor;

@Profile("local")
@Configuration
public class LocalAuthConfig implements WebMvcConfigurer {

    private final AuthArgumentResolver authArgumentResolver;
    private final LocalInterceptor localInterceptor;

    public LocalAuthConfig(final AuthArgumentResolver authArgumentResolver, final LocalInterceptor localInterceptor) {
        this.authArgumentResolver = authArgumentResolver;
        this.localInterceptor = localInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(localInterceptor);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }
}
