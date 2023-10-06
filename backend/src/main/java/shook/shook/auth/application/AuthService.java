package shook.shook.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shook.shook.auth.application.dto.TokenPair;
import shook.shook.member.application.MemberService;
import shook.shook.member.domain.Member;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberService memberService;
    private final OAuthProviderFinder oauthProviderFinder;
    private final TokenService tokenService;

    public TokenPair oAuthLogin(final String oauthType, final String authorizationCode) {
        final OAuthInfoProvider oAuthInfoProvider = oauthProviderFinder.getOAuthInfoProvider(oauthType);

        final String accessTokenResponse = oAuthInfoProvider.getAccessToken(authorizationCode);
        final String memberInfo = oAuthInfoProvider.getMemberInfo(accessTokenResponse);

        final Member member = memberService.findByEmail(memberInfo)
            .orElseGet(() -> memberService.register(memberInfo));
        final Long memberId = member.getId();
        final String nickname = member.getNickname();

        return tokenService.updateWithNewTokenPair(memberId, nickname);
    }
}
