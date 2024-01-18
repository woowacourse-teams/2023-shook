package shook.shook.legacy.member.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shook.shook.legacy.auth.ui.argumentresolver.Authenticated;
import shook.shook.legacy.auth.ui.argumentresolver.MemberInfo;

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
}
