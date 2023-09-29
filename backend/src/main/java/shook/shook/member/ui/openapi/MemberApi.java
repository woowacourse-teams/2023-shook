package shook.shook.member.ui.openapi;

import static shook.shook.auth.application.TokenService.EMPTY_REFRESH_TOKEN;
import static shook.shook.auth.application.TokenService.REFRESH_TOKEN_KEY;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.application.dto.NicknameUpdateRequest;
import shook.shook.member.application.dto.NicknameUpdateResponse;

@Tag(name = "Member", description = "회원 관리 API")
public interface MemberApi {

    @Operation(
        summary = "회원 탈퇴",
        description = "회원 탈퇴로 회원을 삭제한다."
    )
    @ApiResponse(
        responseCode = "204",
        description = "회원 탈퇴, 삭제 성공"
    )
    @Parameter(
        name = "member_id",
        description = "삭제할 회원 id",
        required = true
    )
    @DeleteMapping
    ResponseEntity<Void> deleteMember(
        @PathVariable(name = "member_id") final Long memberId,
        @Authenticated final MemberInfo memberInfo
    );

    @Operation(
        summary = "닉네임 변경",
        description = "닉네임을 변경한다."
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "닉네임 변경 성공"
            ),
            @ApiResponse(
                responseCode = "204",
                description = "동일한 닉네임으로 변경하여 변경된 닉네임이 없음"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "중복된 닉네임, 20자가 넘는 닉네임, 공백 닉네임의 이유로 닉네임 변경 실패"
            )
        }
    )
    @Parameters(
        value = {
            @Parameter(
                name = "refreshToken",
                description = "액세스 토큰 재발급을 위한 리프레시 토큰",
                required = true
            ),
            @Parameter(
                name = "authorization",
                description = "인증 헤더",
                required = true
            ),
            @Parameter(
                name = "member_id",
                description = "닉네임을 변경할 회원 id",
                required = true
            ),
            @Parameter(
                name = "nickname",
                description = "변경할 닉네임",
                required = true
            )
        }
    )
    @PatchMapping("/nickname")
    ResponseEntity<NicknameUpdateResponse> updateNickname(
        @CookieValue(value = REFRESH_TOKEN_KEY, defaultValue = EMPTY_REFRESH_TOKEN) final String refreshToken,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String authorization,
        @PathVariable(name = "member_id") final Long memberId,
        @Valid @RequestBody final NicknameUpdateRequest request
    );
}
