package shook.shook.auth.application;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shook.shook.auth.application.dto.GoogleAccessTokenResponse;
import shook.shook.auth.application.dto.GoogleMemberInfoResponse;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.auth.application.dto.TokenPair;
import shook.shook.auth.repository.InMemoryTokenPairRepository;
import shook.shook.member.application.MemberService;
import shook.shook.member.domain.Member;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberService memberService;
    private final GoogleInfoProvider googleInfoProvider;
    private final TokenProvider tokenProvider;
    private final InMemoryTokenPairRepository inMemoryTokenPairRepository;

    public TokenPair login(final String authorizationCode) {
        final GoogleAccessTokenResponse accessTokenResponse =
            googleInfoProvider.getAccessToken(authorizationCode);
        final GoogleMemberInfoResponse memberInfo = googleInfoProvider
            .getMemberInfo(accessTokenResponse.getAccessToken());

        final String userEmail = memberInfo.getEmail();

        final Member member = memberService.findByEmail(userEmail)
            .orElseGet(() -> memberService.register(userEmail));

        final Long memberId = member.getId();
        final String nickname = member.getNickname();
        final String accessToken = tokenProvider.createAccessToken(memberId, nickname);
        final String refreshToken = tokenProvider.createRefreshToken(memberId, nickname);
        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, accessToken);
        return new TokenPair(accessToken, refreshToken);
    }

    public ReissueAccessTokenResponse reissueAccessTokenByRefreshToken(final String refreshToken, final String accessToken) {
        final Claims claims = tokenProvider.parseClaims(refreshToken);
        final Long memberId = claims.get("memberId", Long.class);
        final String nickname = claims.get("nickname", String.class);

        inMemoryTokenPairRepository.validateTokenPair(refreshToken, accessToken);
        final String reissuedAccessToken = tokenProvider.createAccessToken(memberId, nickname);
        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, reissuedAccessToken);
        return new ReissueAccessTokenResponse(reissuedAccessToken);
    }
}
