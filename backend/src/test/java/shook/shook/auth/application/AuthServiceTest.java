package shook.shook.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shook.shook.auth.application.dto.TokenPair;
import shook.shook.auth.repository.InMemoryTokenPairRepository;
import shook.shook.member.application.MemberService;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;

@SpringBootTest
class AuthServiceTest {

    private static Member savedMember;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OAuthProviderFinder oauthProviderFinder;

    @Autowired
    private MemberService memberService;

    @MockBean
    private GoogleInfoProvider googleInfoProvider;

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
        authService = new AuthService(memberService, oauthProviderFinder, tokenProvider, inMemoryTokenPairRepository);
        savedMember = memberRepository.save(new Member("shook@wooteco.com", "shook"));
        refreshToken = tokenProvider.createRefreshToken(savedMember.getId());
        accessToken = tokenProvider.createAccessToken(savedMember.getId(), savedMember.getNickname());
        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, accessToken);
    }

    @AfterEach
    void delete() {
        memberRepository.delete(savedMember);
    }

    @DisplayName("구글 소셜 로그인 시 accessToken과 refreshToken을 반환한다.")
    @Test
    void success_google_login() {
        //given
        final String authAccessToken = "accessToken";
        when(googleInfoProvider.getAccessToken(any(String.class)))
            .thenReturn(authAccessToken);
        final String email = "shook@wooteco.com";
        when(googleInfoProvider.getMemberInfo(any(String.class)))
            .thenReturn(email);

        //when
        final TokenPair result = authService.oAuthLogin("google", "accessCode");

        //then
        final String accessToken = result.getAccessToken();
        final String refreshToken = result.getRefreshToken();
        final Claims accessTokenClaims = tokenProvider.parseClaims(accessToken);
        final Claims refreshTokenClaims = tokenProvider.parseClaims(refreshToken);

        assertThat(accessTokenClaims.get("memberId", Long.class)).isEqualTo(savedMember.getId());
        assertThat(accessTokenClaims.get("nickname", String.class)).isEqualTo(savedMember.getNickname());
        assertThat(refreshTokenClaims.get("memberId", Long.class)).isEqualTo(savedMember.getId());
    }
}
