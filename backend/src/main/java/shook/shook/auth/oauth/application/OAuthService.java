package shook.shook.auth.oauth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shook.shook.auth.jwt.application.TokenProvider;
import shook.shook.auth.oauth.application.dto.GoogleAccessTokenResponse;
import shook.shook.auth.oauth.application.dto.GoogleMemberInfoResponse;
import shook.shook.auth.oauth.application.dto.LoginResponse;
import shook.shook.member.application.MemberService;
import shook.shook.member.domain.Email;
import shook.shook.member.domain.Member;

@RequiredArgsConstructor
@Service
public class OAuthService {

    private final MemberService memberService;
    private final GoogleInfoProvider googleInfoProvider;
    private final TokenProvider tokenProvider;

    public LoginResponse login(final String accessCode) {
        final GoogleAccessTokenResponse accessTokenResponse =
            googleInfoProvider.getAccessToken(accessCode);
        final GoogleMemberInfoResponse memberInfo = googleInfoProvider
            .getMemberInfo(accessTokenResponse.getAccessToken());

        final String userEmail = memberInfo.getEmail();
        final Member member = memberService.findByEmail(new Email(userEmail))
            .orElseGet(() -> memberService.register(userEmail));

        final Long memberId = member.getId();
        final String accessToken = tokenProvider.createAccessToken(memberId);
        final String refreshToken = tokenProvider.createRefreshToken(memberId);
        return new LoginResponse(accessToken, refreshToken);
    }
}
