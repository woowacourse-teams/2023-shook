package shook.shook.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.auth.exception.TokenException;
import shook.shook.auth.repository.InMemoryTokenPairRepository;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;

@SpringBootTest
class TokenServiceTest {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private InMemoryTokenPairRepository inMemoryTokenPairRepository;

    private TokenProvider tokenProvider;
    private TokenService tokenService;
    private Member savedMember;
    private String refreshToken;
    private String accessToken;

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider(
            100000L,
            1000000L,
            "asdfsdsvsdf2esvsdvsdvs23");
        tokenService = new TokenService(tokenProvider, inMemoryTokenPairRepository);
        savedMember = memberRepository.save(new Member("shook@wooteco.com", "shook"));
        refreshToken = tokenProvider.createRefreshToken(savedMember.getId(), savedMember.getNickname());
        accessToken = tokenProvider.createAccessToken(savedMember.getId(), savedMember.getNickname());
        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, accessToken);
    }

    @DisplayName("올바른 refresh 토큰과 access 토큰이 들어오면 access 토큰을 재발급해준다.")
    @Test
    void success_reissue() {
        //given
        //when
        final ReissueAccessTokenResponse result = tokenService.reissueAccessTokenByRefreshToken(refreshToken,
                                                                                                accessToken);

        //then
        final String newAccessToken = result.getAccessToken();
        final Claims accessTokenClaimsBeforeCreation = tokenProvider.parseClaims(accessToken);
        final Claims newAccessTokenClaims = tokenProvider.parseClaims(newAccessToken);

        assertThat(newAccessTokenClaims.get("memberId")).isEqualTo(accessTokenClaimsBeforeCreation.get("memberId"));
        assertThat(newAccessTokenClaims.get("nickname")).isEqualTo(accessTokenClaimsBeforeCreation.get("nickname"));
    }

    @DisplayName("잘못된 refresh 토큰(secret Key가 다른)이 들어오면 예외를 던진다.")
    @Test
    void fail_reissue_invalid_refreshToken() {
        //given
        final TokenProvider inValidTokenProvider = new TokenProvider(
            10L,
            100L,
            "asdzzxcwetg2adfvssd3xZcZXCZvzx");

        final String wrongRefreshToken = inValidTokenProvider.createRefreshToken(savedMember.getId(),
                                                                                 savedMember.getNickname());

        //when
        //then
        assertThatThrownBy(() -> tokenService.reissueAccessTokenByRefreshToken(wrongRefreshToken, accessToken))
            .isInstanceOf(TokenException.NotIssuedTokenException.class);
    }

    @DisplayName("기간이 만료된 refresh 토큰이면 예외를 던진다.")
    @Test
    void fail_reissue_expired_refreshToken() {
        //given
        final TokenProvider inValidTokenProvider = new TokenProvider(
            0,
            0,
            "asdfsdsvsdf2esvsdvsdvs23");

        final String refreshToken = inValidTokenProvider.createRefreshToken(savedMember.getId(),
                                                                            savedMember.getNickname());

        //when
        //then
        assertThatThrownBy(() -> tokenService.reissueAccessTokenByRefreshToken(refreshToken, accessToken))
            .isInstanceOf(TokenException.ExpiredTokenException.class);
    }

    @DisplayName("Bearer 를 제외하고 액세스 토큰을 추출한다.")
    @Test
    void extractAccessToken() {
        // given
        // when
        final String resultAccessToken = tokenService.extractAccessToken("Bearer " + accessToken);

        // then
        assertThat(resultAccessToken).isEqualTo(accessToken);
    }
}
