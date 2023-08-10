package shook.shook.auth.ui.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import shook.shook.auth.exception.AuthorizationException;
import shook.shook.auth.exception.AuthorizationException.InvalidAuthorizationHeaderFormatException;

public class TokenHeaderExtractor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    public static String extractToken(final HttpServletRequest request) {
        final String authorization = request.getHeader(AUTHORIZATION_HEADER);
        final String[] tokenElement = validateAuthorizationHeader(authorization);
        return tokenElement[1];
    }

    private static String[] validateAuthorizationHeader(final String authorizationHeader) {
        if (Objects.isNull(authorizationHeader) || authorizationHeader.isBlank()) {
            throw new AuthorizationException.TokenNotFoundException();
        }

        final String[] tokenElement = authorizationHeader.split(" ");
        if (tokenElement.length != 2 || !tokenElement[0].equals(TOKEN_PREFIX)) {
            throw new InvalidAuthorizationHeaderFormatException();
        }
        return tokenElement;
    }
}
