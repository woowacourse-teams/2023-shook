package shook.shook.auth.ui.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import shook.shook.auth.exception.AuthorizationException.AccessTokenNotFoundException;
import shook.shook.auth.exception.AuthorizationException.InvalidAuthorizationHeaderFormatException;
import shook.shook.util.StringChecker;

public class TokenHeaderExtractor {

    private static final int TOKEN_PREFIX_INDEX = 0;
    private static final int TOKEN_FORMAT_SIZE = 2;
    private static final int TOKEN_INDEX = 1;
    private static final String TOKEN_PREFIX = "Bearer";

    public static String extractToken(final HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateAuthorizationHeader(authorization);
        return getToken(authorization);
    }

    private static void validateAuthorizationHeader(final String authorizationHeader) {
        if (StringChecker.isNullOrBlank(authorizationHeader)) {
            throw new AccessTokenNotFoundException();
        }

        final String[] tokenElement = authorizationHeader.split(" ");
        if (tokenElement.length != TOKEN_FORMAT_SIZE ||
            !tokenElement[TOKEN_PREFIX_INDEX].equals(TOKEN_PREFIX)) {
            throw new InvalidAuthorizationHeaderFormatException();
        }
    }

    private static String getToken(final String authorizationHeaderValue) {
        final String[] tokenElement = authorizationHeaderValue.split(" ");
        return tokenElement[TOKEN_INDEX];
    }
}
