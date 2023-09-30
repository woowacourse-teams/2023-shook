package shook.shook.auth.ui;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

    private final int cookieAge;

    public CookieProvider(@Value("${cookie.valid-time}") final int cookieAge) {
        this.cookieAge = cookieAge;
    }

    public Cookie createRefreshTokenCookie(final String refreshToken) {
        final Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setMaxAge(cookieAge);
        cookie.setPath("/api");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        return cookie;
    }
}
