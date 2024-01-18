package shook.shook.legacy.auth.application;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shook.shook.legacy.auth.exception.TokenException;
import shook.shook.legacy.auth.repository.InMemoryTokenPairRepository;

@RequiredArgsConstructor
@Component
public class TokenPairScheduler {

    private final TokenProvider tokenProvider;
    private final InMemoryTokenPairRepository inMemoryTokenPairRepository;

    @Scheduled(cron = "${schedules.in-memory-token.cron}")
    public void removeExpiredTokenPair() {
        final Set<String> refreshTokens = inMemoryTokenPairRepository.getTokenPairs().keySet();
        for (String refreshToken : refreshTokens) {
            try {
                tokenProvider.parseClaims(refreshToken);
            } catch (TokenException.ExpiredTokenException e) {
                inMemoryTokenPairRepository.delete(refreshToken);
            }
        }
    }
}
