package shook.shook.auth.oauth.application;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        final ResponseEntity<GoogleAccessTokenResponse> accessTokenResponse =
            googleInfoProvider.getAccessToken(accessCode);
        final ResponseEntity<GoogleMemberInfoResponse> memberInfo = googleInfoProvider
            .getMemberInfo(Objects.requireNonNull(accessTokenResponse.getBody()).getAccessToken());

        final String userEmail = Objects.requireNonNull(memberInfo.getBody()).getEmail();
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
