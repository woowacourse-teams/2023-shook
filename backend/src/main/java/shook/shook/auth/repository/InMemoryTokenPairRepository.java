package shook.shook.auth.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import shook.shook.auth.exception.TokenException;
import shook.shook.auth.exception.TokenException.TokenPairNotMatchingException;

@Repository
public class InMemoryTokenPairRepository {

    private final Map<String, String> tokenPairs = new ConcurrentHashMap<>();

    public void validateTokenPair(final String refreshToken, final String accessToken) {
        if (!tokenPairs.containsKey(refreshToken)) {
            throw new TokenException.RefreshTokenNotFoundException(Map.of("wrongRefreshToken", refreshToken));
        }
        if (!tokenPairs.get(refreshToken).equals(accessToken)) {
            throw new TokenPairNotMatchingException(Map.of("wrongAccessToken", accessToken));
        }
    }

    public void add(final String refreshToken, final String accessToken) {
        tokenPairs.put(refreshToken, accessToken);
    }

    public void update(final String refreshToken, final String accessToken) {
        tokenPairs.put(refreshToken, accessToken);
    }
}
