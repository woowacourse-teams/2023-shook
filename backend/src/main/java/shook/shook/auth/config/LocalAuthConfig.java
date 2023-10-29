package shook.shook.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shook.shook.auth.ui.interceptor.LocalInterceptor;

@Profile("local")
@Configuration
public class LocalAuthConfig implements WebMvcConfigurer {

    private final LocalInterceptor localInterceptor;

    public LocalAuthConfig(final LocalInterceptor localInterceptor) {
        this.localInterceptor = localInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(localInterceptor);
    }
}
