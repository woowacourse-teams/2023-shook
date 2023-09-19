package shook.shook.auth.application;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shook.shook.auth.exception.TokenException;
import shook.shook.auth.repository.InMemoryTokenPairRepository;

@RequiredArgsConstructor
@Component
public class TokenPairScheduler {

    private final TokenProvider tokenProvider;
    private final InMemoryTokenPairRepository inMemoryTokenPairRepository;

    @Scheduled(cron = "${schedules.cron}")
    public void renewInMemoryTokenPairRepository() {
        final Set<String> refreshTokens = inMemoryTokenPairRepository.getTokenPairs().keySet();
        refreshTokens.forEach(refreshToken -> {
            try {
                tokenProvider.parseClaims(refreshToken);
            } catch (TokenException.ExpiredTokenException e) {
                inMemoryTokenPairRepository.delete(refreshToken);
            }
        });
    }
}
