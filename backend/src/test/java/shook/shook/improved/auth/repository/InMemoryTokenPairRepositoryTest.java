package shook.shook.improved.auth.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shook.shook.improved.auth.exception.TokenException.RefreshTokenNotFoundException;
import shook.shook.improved.auth.exception.TokenException.TokenPairNotMatchingException;

class InMemoryTokenPairRepositoryTest {

    @DisplayName("refreshToken과 accessToken이 올바른 쌍을 가지고 있으면 예외를 처리하지 않는다.")
    @Test
    void successValidateTokenPair() {
        // given
        final InMemoryTokenPairRepository inMemoryTokenPairRepository = new InMemoryTokenPairRepository();
        inMemoryTokenPairRepository.addOrUpdateTokenPair("refreshToken", "accessToken");

        // when
        // then
        assertDoesNotThrow(() -> inMemoryTokenPairRepository.validateTokenPair("refreshToken", "accessToken"));
    }

    @DisplayName("존재하지 않는 refreshToken이면 예외를 발생한다.")
    @Test
    void failValidateTokenPair_refreshTokenNotExist() {
        // given
        final InMemoryTokenPairRepository inMemoryTokenPairRepository = new InMemoryTokenPairRepository();
        inMemoryTokenPairRepository.addOrUpdateTokenPair("refreshToken", "accessToken");

        // when
        // then
        assertThatThrownBy(() -> inMemoryTokenPairRepository.validateTokenPair("wrongRefreshToken", "accessToken"))
            .isInstanceOf(RefreshTokenNotFoundException.class);
    }

    @DisplayName("refreshToken에 해당하는 accessToken이 매칭되지 않으면 예외를 발생한다.")
    @Test
    void failValidateTokenPair_notMatching() {
        // given
        final InMemoryTokenPairRepository inMemoryTokenPairRepository = new InMemoryTokenPairRepository();
        inMemoryTokenPairRepository.addOrUpdateTokenPair("refreshToken", "accessToken");

        // when
        // then
        assertThatThrownBy(() -> inMemoryTokenPairRepository.validateTokenPair("refreshToken", "wrongAccessToken"))
            .isInstanceOf(TokenPairNotMatchingException.class);
    }
}
