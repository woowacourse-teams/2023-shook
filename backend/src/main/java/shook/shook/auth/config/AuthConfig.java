package shook.shook.auth.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shook.shook.auth.ui.argumentresolver.AuthArgumentResolver;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final AuthArgumentResolver authArgumentResolver;

    public AuthConfig(final AuthArgumentResolver authArgumentResolver) {
        this.authArgumentResolver = authArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }
}
