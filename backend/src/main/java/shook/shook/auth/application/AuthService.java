package shook.shook.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shook.shook.auth.application.dto.TokenPair;
import shook.shook.auth.repository.InMemoryTokenPairRepository;
import shook.shook.member.application.MemberService;
import shook.shook.member.domain.Member;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberService memberService;
    private final OAuthProviderFinder oauthProviderFinder;
    private final TokenProvider tokenProvider;
    private final InMemoryTokenPairRepository inMemoryTokenPairRepository;

    public TokenPair oAuthLogin(final String oauthType, final String authorizationCode) {
        final OAuthInfoProvider oAuthInfoProvider = oauthProviderFinder.getOAuthInfoProvider(oauthType);

        final String accessTokenResponse = oAuthInfoProvider.getAccessToken(authorizationCode);
        final String memberInfo = oAuthInfoProvider.getMemberInfo(accessTokenResponse);

        final Member member = memberService.findByEmail(memberInfo)
            .orElseGet(() -> memberService.register(memberInfo));

        final Long memberId = member.getId();
        final String nickname = member.getNickname();
        final String accessToken = tokenProvider.createAccessToken(memberId, nickname);
        final String refreshToken = tokenProvider.createRefreshToken(memberId, nickname);
        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, accessToken);
        return new TokenPair(accessToken, refreshToken);
    }
}
