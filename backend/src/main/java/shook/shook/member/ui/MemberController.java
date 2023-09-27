package shook.shook.member.ui;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.auth.application.dto.TokenPair;
import shook.shook.auth.ui.CookieProvider;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.application.MemberService;
import shook.shook.member.application.dto.NicknameUpdateRequest;
import shook.shook.member.ui.openapi.MemberApi;

@RequiredArgsConstructor
@RequestMapping("/members/{member_id}")
@RestController
public class MemberController implements MemberApi {

    private final MemberService memberService;
    private final CookieProvider cookieProvider;

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
        @PathVariable(name = "member_id") final Long memberId,
        @Authenticated final MemberInfo memberInfo
    ) {
        memberService.deleteById(memberId, memberInfo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/nickname")
    public ResponseEntity<ReissueAccessTokenResponse> updateNickname(
        @PathVariable(name = "member_id") final Long memberId,
        @Authenticated final MemberInfo memberInfo,
        @Valid @RequestBody final NicknameUpdateRequest request,
        final HttpServletResponse response
    ) {
        final TokenPair tokenPair = memberService.updateNickname(memberId, memberInfo, request);

        if (tokenPair == null) {
            return ResponseEntity.noContent().build();
        }

        final Cookie cookie = cookieProvider.createRefreshTokenCookie(tokenPair.getRefreshToken());
        response.addCookie(cookie);
        final ReissueAccessTokenResponse reissueResponse = new ReissueAccessTokenResponse(
            tokenPair.getAccessToken());

        return ResponseEntity.ok(reissueResponse);
    }
}
