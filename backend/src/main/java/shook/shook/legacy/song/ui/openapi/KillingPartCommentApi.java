package shook.shook.legacy.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shook.shook.legacy.auth.ui.argumentresolver.Authenticated;
import shook.shook.legacy.auth.ui.argumentresolver.MemberInfo;
import shook.shook.legacy.song.application.killingpart.dto.KillingPartCommentRegisterRequest;
import shook.shook.legacy.song.application.killingpart.dto.KillingPartCommentResponse;

@Tag(name = "Killing Part Comment", description = "킬링파트 댓글 API")
public interface KillingPartCommentApi {

    @Operation(
        summary = "킬링파트 댓글 등록",
        description = "킬링파트의 댓글을 등록한다."
    )
    @ApiResponse(
        responseCode = "201",
        description = "킬링파트의 댓글 등록 성공"
    )
    @Parameters(
        value = {
            @Parameter(
                name = "killing_part_id",
                description = "킬링파트 id",
                required = true
            ),
            @Parameter(
                name = "request",
                description = "킬링파트 댓글 등록 요청",
                required = true
            )
        }
    )
    @PostMapping
    ResponseEntity<Void> registerKillingPartComment(
        @PathVariable(name = "killing_part_id") final Long killingPartId,
        @Valid @RequestBody final KillingPartCommentRegisterRequest request,
        @Authenticated final MemberInfo memberInfo
    );

    @Operation(
        summary = "킬링파트 댓글 조회",
        description = "킬링파트의 댓글을 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "킬링파트의 댓글 조회 성공"
    )
    @Parameter(
        name = "killing_part_id",
        description = "킬링파트 id",
        required = true
    )
    @GetMapping
    ResponseEntity<List<KillingPartCommentResponse>> findKillingPartComments(
        @PathVariable(name = "killing_part_id") final Long killingPartId
    );
}
