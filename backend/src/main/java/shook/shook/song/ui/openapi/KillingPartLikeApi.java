package shook.shook.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.song.application.killingpart.dto.KillingPartLikeRequest;

@Tag(name = "Killing Part Like", description = "킬링파트 좋아요 API")
public interface KillingPartLikeApi {

    @Operation(
        summary = "킬링파트 좋아요 등록 / 취소",
        description = "킬링파트 좋아요를 등록 / 취소한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "킬링파트 좋아요 성공"
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
                description = "킬링파트 좋아요 등록 / 취소 요청",
                required = true
            )
        }
    )
    @PutMapping
    ResponseEntity<Void> createLikeOnKillingPart(
        @PathVariable(name = "killing_part_id") final Long killingPartId,
        @Valid @RequestBody final KillingPartLikeRequest request,
        @Authenticated final MemberInfo memberInfo
    );
}
