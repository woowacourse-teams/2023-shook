package shook.shook.auth.application;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shook.shook.auth.application.dto.GoogleAccessTokenResponse;
import shook.shook.auth.application.dto.GoogleMemberInfoResponse;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.auth.application.dto.TokenPair;
import shook.shook.member.application.MemberService;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.Nickname;

@RequiredArgsConstructor
@Service
//이 부분도 Controller 와 같은 리뷰입니다.
public class GoogleAuthService {

    private final MemberService memberService;
    private final GoogleInfoProvider googleInfoProvider;
    private final TokenProvider tokenProvider;

    public TokenPair login(final String authorizationCode) {
        final GoogleAccessTokenResponse accessTokenResponse =
            googleInfoProvider.getAccessToken(authorizationCode);
        final GoogleMemberInfoResponse memberInfo = googleInfoProvider
            .getMemberInfo(accessTokenResponse.getAccessToken());

        final String userEmail = memberInfo.getEmail();

        // register 메서드에서는 String 을 사용하고 있어요~ 통일해도 좋을 것 같아요
        // AuthService 에서 굳이 Email 의 비즈니스 로직을 알지 않아도 될 것 같아요
        final Member member = memberService.findByEmail(userEmail)
            .orElseGet(() -> memberService.register(userEmail));

        //unwrap 될 필요 없을 것 같아요~
        final Long memberId = member.getId();
        final String nickname = member.getNickname();
        //(사소) 토큰 생성 방식의 변경에 유연하기 위해서 member 를 파라미터로 주는 것도 나쁘지 않아보여요~
        final String accessToken = tokenProvider.createAccessToken(memberId, nickname);
        final String refreshToken = tokenProvider.createRefreshToken(memberId, nickname);
        return new TokenPair(accessToken, refreshToken);
    }

    //메서드명을 조금 더 확실한 네이밍으로 바꾸면 어떨까요?
    public ReissueAccessTokenResponse reissueAccessTokenByRefreshToken(final String refreshToken) {
        final Claims claims = tokenProvider.parseClaims(refreshToken);
        final Long memberId = claims.get("memberId", Long.class);
        final String nickname = claims.get("nickname", String.class);
        memberService.findByIdAndNicknameThrowIfNotExist(memberId, new Nickname(nickname));

        final String accessToken = tokenProvider.createAccessToken(memberId, nickname);
        //클래스명 변경 추천에 따른 변경사항
        return new ReissueAccessTokenResponse(accessToken);
    }
}
