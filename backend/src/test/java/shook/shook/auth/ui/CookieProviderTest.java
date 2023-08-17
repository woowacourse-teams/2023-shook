package shook.shook.auth.ui;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CookieProviderTest {

    private final CookieProvider cookieProvider = new CookieProvider(640000);

    @DisplayName("refreshToken이 주어지면 Cookie를 생성한다.")
    @Test
    void success_create_cookie() {
        //given
        final String refreshToken = "shook";

        //when
        final Cookie cookie = cookieProvider.createRefreshTokenCookie(refreshToken);

        //then
        assertThat(cookie.getValue()).isEqualTo(refreshToken);
    }
}
