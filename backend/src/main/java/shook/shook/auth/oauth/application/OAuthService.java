package shook.shook.auth.oauth.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shook.shook.auth.jwt.application.TokenProvider;
import shook.shook.auth.oauth.application.dto.GoogleAccessTokenResponse;
import shook.shook.auth.oauth.application.dto.GoogleMemberInfoResponse;
import shook.shook.auth.oauth.application.dto.OAuthResponse;
import shook.shook.member.application.MemberService;
import shook.shook.member.domain.Email;
import shook.shook.member.domain.Member;

@RequiredArgsConstructor
@Service
public class OAuthService {

    private final MemberService memberService;
    private final GoogleInfoProvider googleInfoProvider;
    private final TokenProvider tokenProvider;

    public OAuthResponse login(final String accessCode) {
        final GoogleAccessTokenResponse accessTokenResponse =
            googleInfoProvider.getAccessToken(accessCode);
        final GoogleMemberInfoResponse memberInfo = googleInfoProvider
            .getMemberInfo(accessTokenResponse.getAccessToken());

        final String userEmail = memberInfo.getEmail();
        final Optional<Member> registeredMember = memberService.findByEmail(new Email(userEmail));

        if (registeredMember.isPresent()) {
            final Long memberId = registeredMember.get().getId();
            final String accessToken = OAuthService.this.tokenProvider.createAccessToken(memberId);
            final String refreshToken = OAuthService.this.tokenProvider.createRefreshToken(
                memberId);
            return new OAuthResponse(userEmail, accessToken, refreshToken);
        }
        return new OAuthResponse(userEmail, null, null);
    }
}
