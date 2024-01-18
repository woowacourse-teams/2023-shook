package shook.shook.legacy.auth.ui.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;

public class TokenHeaderExtractor {

    private static final int TOKEN_PREFIX_INDEX = 0;
    private static final int TOKEN_FORMAT_SIZE = 2;
    private static final int TOKEN_INDEX = 1;
    private static final String TOKEN_PREFIX = "Bearer";

    public static Optional<String> extractToken(final HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isEmpty(authorization)) {
            return Optional.empty();
        }
        return getToken(authorization.split(" "));
    }

    private static Optional<String> getToken(final String[] parts) {
        if (parts.length != TOKEN_FORMAT_SIZE ||
            !parts[TOKEN_PREFIX_INDEX].equals(TOKEN_PREFIX)) {
            return Optional.empty();
        }
        return Optional.ofNullable(parts[TOKEN_INDEX]);
    }
}
