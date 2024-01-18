package shook.shook.legacy.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import shook.shook.auth.application.TokenPairScheduler;
import shook.shook.auth.application.TokenProvider;
import shook.shook.auth.repository.InMemoryTokenPairRepository;

@EnableScheduling
@SpringBootTest
class TokenPairSchedulerTest {

    @Autowired
    private InMemoryTokenPairRepository inMemoryTokenPairRepository;

    private TokenProvider tokenProvider;
    private TokenProvider expiredTokenProvider;

    @BeforeEach
    void setting() {
        tokenProvider = new TokenProvider(1200, 634000, "asdkfwofk23ksdfowsrk4sdkf");
        expiredTokenProvider = new TokenProvider(0, 0, "asdkfwofk23ksdfowsrk4sdkf");
    }

    @AfterEach
    void clear() {
        inMemoryTokenPairRepository.clear();
    }

    @DisplayName("tokenPairSechduler를 통해 inMemoryTokenPairRepository를 갱신한다.")
    @Test
    void renewInMemoryTokenPairRepository() {
        // given
        final String refreshToken = tokenProvider.createRefreshToken(1L, "shook");
        final String accessToken = tokenProvider.createAccessToken(1L, "shook");
        final String expiredRefreshToken = expiredTokenProvider.createRefreshToken(2L, "expiredShook");
        final String expiredAccessToken = expiredTokenProvider.createAccessToken(2L, "expiredShook");

        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, accessToken);
        inMemoryTokenPairRepository.addOrUpdateTokenPair(expiredRefreshToken, expiredAccessToken);

        // when
        final TokenPairScheduler tokenPairScheduler
            = new TokenPairScheduler(tokenProvider, inMemoryTokenPairRepository);
        tokenPairScheduler.removeExpiredTokenPair();

        // then
        final Map<String, String> tokenPairs = inMemoryTokenPairRepository.getTokenPairs();

        assertThat(tokenPairs.size()).isOne();
        assertThat(tokenPairs.get(refreshToken)).isEqualTo(accessToken);
        assertThat(tokenPairs.containsKey(expiredRefreshToken)).isFalse();
    }

    @Test
    @DisplayName("1초마다 동작하는 scheduler로 inMemoryTokenPairRepository를 갱신한다.")
    void renewInMemoryTokenPairRepositoryWithScheduler() {
        // given
        final String refreshToken = tokenProvider.createRefreshToken(1L, "shook");
        final String accessToken = tokenProvider.createAccessToken(1L, "shook");
        final String expiredRefreshToken = expiredTokenProvider.createRefreshToken(2L, "expiredShook");
        final String expiredAccessToken = expiredTokenProvider.createAccessToken(2L, "expiredShook");

        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, accessToken);
        inMemoryTokenPairRepository.addOrUpdateTokenPair(expiredRefreshToken, expiredAccessToken);

        // when
        final Map<String, String> tokenPairs = inMemoryTokenPairRepository.getTokenPairs();
        // then
        Awaitility.await()
            .atMost(2, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                assertThat(tokenPairs.size()).isOne();
                assertThat(tokenPairs.get(refreshToken)).isEqualTo(accessToken);
                assertThat(tokenPairs.containsKey(expiredRefreshToken)).isFalse();
            });
    }
}
