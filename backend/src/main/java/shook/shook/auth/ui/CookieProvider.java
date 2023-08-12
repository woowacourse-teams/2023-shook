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
        cookie.setPath("/token");
        cookie.setHttpOnly(true);
        // TODO: 2023/08/11 추후에 setSecure 옵션 넣기
        return cookie;
    }
}
