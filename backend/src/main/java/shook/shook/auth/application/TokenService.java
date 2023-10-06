package shook.shook.auth.application;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.auth.application.dto.TokenPair;
import shook.shook.auth.repository.InMemoryTokenPairRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TokenService {

    public static final String TOKEN_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;
    private final InMemoryTokenPairRepository inMemoryTokenPairRepository;

    public String createAccessToken(final Long memberId, final String nickname) {
        return tokenProvider.createAccessToken(memberId, nickname);
    }

    public String createRefreshToken(final Long memberId, final String nickname) {
        return tokenProvider.createRefreshToken(memberId, nickname);
    }

    public ReissueAccessTokenResponse reissueAccessTokenByRefreshToken(final String refreshToken,
                                                                       final String accessToken) {
        inMemoryTokenPairRepository.validateTokenPair(refreshToken, accessToken);
        final Claims claims = tokenProvider.parseClaims(refreshToken);
        final Long memberId = claims.get("memberId", Long.class);
        final String nickname = claims.get("nickname", String.class);

        final String reissuedAccessToken = tokenProvider.createAccessToken(memberId, nickname);
        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, reissuedAccessToken);

        return new ReissueAccessTokenResponse(reissuedAccessToken);
    }

    public TokenPair updateWithNewTokenPair(final Long memberId, final String nickname) {
        final String accessToken = createAccessToken(memberId, nickname);
        final String refreshToken = createRefreshToken(memberId, nickname);
        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, accessToken);

        return new TokenPair(refreshToken, accessToken);
    }
}
