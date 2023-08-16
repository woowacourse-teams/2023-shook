package shook.shook.auth.application;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shook.shook.auth.application.dto.GoogleAccessTokenResponse;
import shook.shook.auth.application.dto.GoogleMemberInfoResponse;
import shook.shook.auth.application.dto.TokenInfo;
import shook.shook.auth.application.dto.TokenReissueResponse;
import shook.shook.member.application.MemberService;
import shook.shook.member.domain.Email;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.Nickname;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberService memberService;
    private final GoogleInfoProvider googleInfoProvider;
    private final TokenProvider tokenProvider;

    public TokenInfo login(final String accessCode) {
        final GoogleAccessTokenResponse accessTokenResponse =
            googleInfoProvider.getAccessToken(accessCode);
        final GoogleMemberInfoResponse memberInfo = googleInfoProvider
            .getMemberInfo(accessTokenResponse.getAccessToken());

        final String userEmail = memberInfo.getEmail();
        final Member member = memberService.findByEmail(new Email(userEmail))
            .orElseGet(() -> memberService.register(userEmail));

        final long memberId = member.getId();
        final String nickname = member.getNickname();
        final String accessToken = tokenProvider.createAccessToken(memberId, nickname);
        final String refreshToken = tokenProvider.createRefreshToken(memberId, nickname);
        return new TokenInfo(accessToken, refreshToken);
    }

    public TokenReissueResponse reissueToken(final String refreshToken) {
        final Claims claims = tokenProvider.parseClaims(refreshToken);
        final Long memberId = claims.get("memberId", Long.class);
        final String nickname = claims.get("nickname", String.class);
        memberService.findByIdAndNickname(memberId, new Nickname(nickname));

        final String accessToken = tokenProvider.createAccessToken(memberId, nickname);
        return new TokenReissueResponse(accessToken);
    }
}
