package shook.shook.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shook.shook.auth.application.dto.GoogleAccessTokenResponse;
import shook.shook.auth.application.dto.GoogleMemberInfoResponse;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.auth.application.dto.TokenPair;
import shook.shook.auth.exception.TokenException;
import shook.shook.auth.repository.InMemoryTokenPairRepository;
import shook.shook.member.application.MemberService;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;

@SpringBootTest
class AuthServiceTest {

    private static Member savedMember;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private GoogleInfoProvider googleInfoProvider;

    @MockBean
    private KakaoInfoProvider kakaoInfoProvider;

    @Autowired
    private MemberService memberService;

    @Autowired
    private InMemoryTokenPairRepository inMemoryTokenPairRepository;

    private TokenProvider tokenProvider;

    private String refreshToken;

    private String accessToken;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider(
            100000L,
            1000000L,
            "asdfsdsvsdf2esvsdvsdvs23");
        authService = new AuthService(memberService, googleInfoProvider, kakaoInfoProvider, tokenProvider);
        savedMember = memberRepository.save(new Member("shook@wooteco.com", "shook"));
        refreshToken = tokenProvider.createRefreshToken(savedMember.getId(), savedMember.getNickname());
        accessToken = tokenProvider.createAccessToken(savedMember.getId(), savedMember.getNickname());
        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, accessToken);
        authService = new AuthService(memberService, googleInfoProvider, tokenProvider, inMemoryTokenPairRepository);
    }

    @AfterEach
    void delete() {
        memberRepository.delete(savedMember);
    }

    @DisplayName("소셜 로그인 시 accessToken과 refreshToken을 반환한다.")
    @Test
    void success_login() {
        //given
        final GoogleAccessTokenResponse accessTokenResponse =
            new GoogleAccessTokenResponse("accessToken");
        when(googleInfoProvider.getAccessToken(any(String.class)))
            .thenReturn(accessTokenResponse);

        final GoogleMemberInfoResponse memberInfoResponse =
            new GoogleMemberInfoResponse("shook@wooteco.com", true);
        when(googleInfoProvider.getMemberInfo(any(String.class)))
            .thenReturn(memberInfoResponse);

        //when
        final TokenPair result = authService.login("accessCode");

        //then

        final String accessToken = tokenProvider.createAccessToken(savedMember.getId(),
            savedMember.getNickname());
        final String refreshToken = tokenProvider.createRefreshToken(savedMember.getId(),
            savedMember.getNickname());

        assertThat(result.getAccessToken()).isEqualTo(accessToken);
        assertThat(result.getRefreshToken()).isEqualTo(refreshToken);
        assertDoesNotThrow(() -> inMemoryTokenPairRepository.validateTokenPair(refreshToken, accessToken));
    }

    @DisplayName("올바른 refresh 토큰과 access 토큰이 들어오면 access 토큰을 재발급해준다.")
    @Test
    void success_reissue() {
        //given
        //when
        final ReissueAccessTokenResponse result = authService.reissueAccessTokenByRefreshToken(
            refreshToken, accessToken);

        //then
        final String accessToken = tokenProvider.createAccessToken(
            savedMember.getId(),
            savedMember.getNickname());

        assertThat(result.getAccessToken()).isEqualTo(accessToken);
    }

    @DisplayName("잘못된 refresh 토큰(secret Key가 다른)이 들어오면 예외를 던진다.")
    @Test
    void fail_reissue_invalid_refreshToken() {
        //given
        final TokenProvider inValidTokenProvider = new TokenProvider(
            10L,
            100L,
            "asdzzxcwetg2adfvssd3xZcZXCZvzx");

        final String wrongRefreshToken = inValidTokenProvider.createRefreshToken(
            savedMember.getId(),
            savedMember.getNickname());

        //when
        //then
        assertThatThrownBy(() -> authService.reissueAccessTokenByRefreshToken(wrongRefreshToken, accessToken))
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

        final String refreshToken = inValidTokenProvider.createRefreshToken(
            savedMember.getId(),
            savedMember.getNickname());

        //when
        //then
        assertThatThrownBy(() -> authService.reissueAccessTokenByRefreshToken(refreshToken, accessToken))
            .isInstanceOf(TokenException.ExpiredTokenException.class);
    }
}
