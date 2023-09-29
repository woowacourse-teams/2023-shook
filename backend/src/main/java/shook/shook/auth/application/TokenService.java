package shook.shook.auth.application;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.auth.exception.AuthorizationException;
import shook.shook.auth.repository.InMemoryTokenPairRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TokenService {

    public static final String EMPTY_REFRESH_TOKEN = "none";
    public static final String REFRESH_TOKEN_KEY = "refreshToken";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;
    private final InMemoryTokenPairRepository inMemoryTokenPairRepository;

    public void validateRefreshToken(final String refreshToken) {
        if (refreshToken.equals(EMPTY_REFRESH_TOKEN)) {
            throw new AuthorizationException.RefreshTokenNotFoundException();
        }
    }

    public Long extractMemberId(final String authorization) {
        final String accessToken = extractAccessToken(authorization);
        final Claims claims = tokenProvider.parseClaims(accessToken);

        return claims.get("memberId", Long.class);
    }

    public String extractAccessToken(final String authorization) {
        return authorization.substring(TOKEN_PREFIX.length());
    }

    public ReissueAccessTokenResponse reissueAccessTokenByRefreshToken(final String refreshToken,
                                                                       final String accessToken) {
        final Claims claims = tokenProvider.parseClaims(refreshToken);
        final Long memberId = claims.get("memberId", Long.class);
        final String nickname = claims.get("nickname", String.class);

        inMemoryTokenPairRepository.validateTokenPair(refreshToken, accessToken);
        final String reissuedAccessToken = tokenProvider.createAccessToken(memberId, nickname);
        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, reissuedAccessToken);

        return new ReissueAccessTokenResponse(reissuedAccessToken);
    }

    public ReissueAccessTokenResponse reissueAccessTokenByRefreshTokenByNickname(final String refreshToken,
                                                                                 final String accessToken,
                                                                                 final String nickname) {
        final Claims claims = tokenProvider.parseClaims(refreshToken);
        final Long memberId = claims.get("memberId", Long.class);

        inMemoryTokenPairRepository.validateTokenPair(refreshToken, accessToken);
        final String reissuedAccessToken = tokenProvider.createAccessToken(memberId, nickname);
        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, reissuedAccessToken);

        return new ReissueAccessTokenResponse(reissuedAccessToken);
    }
}
