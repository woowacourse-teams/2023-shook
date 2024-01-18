package shook.shook.legacy.member_part.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shook.shook.legacy.auth.ui.argumentresolver.Authenticated;
import shook.shook.legacy.auth.ui.argumentresolver.MemberInfo;
import shook.shook.legacy.member_part.application.dto.MemberPartRegisterRequest;

@Tag(name = "Member Part", description = "멤버 파트 API")
public interface MemberPartApi {

    @Operation(
        summary = "멤버 파트 등록",
        description = "멤버 파트를 등록한다."
    )
    @ApiResponse(
        responseCode = "201",
        description = "멤버 파트 등록 성공"
    )
    @Parameters(
        value = {
            @Parameter(
                name = "songId",
                description = "노래 ID",
                required = true
            ),
            @Parameter(
                name = "memberId",
                description = "회원 ID",
                required = true,
                hidden = true
            ),
            @Parameter(
                name = "request",
                description = "멤버 파트 등록 요청",
                required = true
            )
        }
    )
    @PostMapping
    ResponseEntity<Void> register(
        @PathVariable(name = "song_id") final Long songId,
        @Authenticated final MemberInfo memberInfo,
        @Valid final MemberPartRegisterRequest request
    );
}
