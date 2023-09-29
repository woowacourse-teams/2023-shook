package shook.shook.member.ui;

import static shook.shook.auth.application.TokenService.EMPTY_REFRESH_TOKEN;
import static shook.shook.auth.application.TokenService.REFRESH_TOKEN_KEY;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.application.TokenService;
import shook.shook.auth.application.dto.ReissueAccessTokenResponse;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.application.MemberService;
import shook.shook.member.application.dto.NicknameUpdateRequest;
import shook.shook.member.application.dto.NicknameUpdateResponse;
import shook.shook.member.ui.openapi.MemberApi;

@RequiredArgsConstructor
@RequestMapping("/members/{member_id}")
@RestController
public class MemberController implements MemberApi {

    private final MemberService memberService;
    private final TokenService tokenService;

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
        @PathVariable(name = "member_id") final Long memberId,
        @Authenticated final MemberInfo memberInfo
    ) {
        memberService.deleteById(memberId, memberInfo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/nickname")
    public ResponseEntity<NicknameUpdateResponse> updateNickname(
        @CookieValue(value = REFRESH_TOKEN_KEY, defaultValue = EMPTY_REFRESH_TOKEN) final String refreshToken,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String authorization,
        @PathVariable(name = "member_id") final Long memberId,
        @Valid @RequestBody final NicknameUpdateRequest request
    ) {
        tokenService.validateRefreshToken(refreshToken);
        final Long requestMemberId = tokenService.extractMemberId(authorization);
        final String accessToken = tokenService.extractAccessToken(authorization);
        final String updatedNickname = memberService.updateNickname(memberId, requestMemberId, request);
        if (updatedNickname == null) {
            return ResponseEntity.noContent().build();
        }

        final ReissueAccessTokenResponse tokenResponse = tokenService.reissueAccessTokenByRefreshTokenByNickname(
            refreshToken, accessToken, updatedNickname);
        final NicknameUpdateResponse response = new NicknameUpdateResponse(tokenResponse.getAccessToken(),
                                                                           updatedNickname);
        return ResponseEntity.ok(response);
    }
}
