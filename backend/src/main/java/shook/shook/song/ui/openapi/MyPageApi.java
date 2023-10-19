package shook.shook.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.song.application.dto.LikedKillingPartResponse;
import shook.shook.song.application.dto.MyPartsResponse;

import java.util.List;

@Tag(name = "MyPage", description = "마이페이지 API")
public interface MyPageApi {

    @Operation(
            summary = "마이페이지 좋아요 리스트 조회",
            description = "좋아요한 킬링파트 리스트를 전체 조회한다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "좋아요한 킬링파트 리스트 조회 성공"
    )
    @GetMapping
    ResponseEntity<List<LikedKillingPartResponse>> getMemberLikedKillingParts(
            @Authenticated final MemberInfo memberInfo
    );

    @Operation(
            summary = "마이페이지 내 마이파트 리스트 조회",
            description = "마이 파트로 등록한 킬링파트 리스트를 전체 조회한다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "마이파트 리스트 조회 성공"
    )
    @GetMapping
    public ResponseEntity<List<MyPartsResponse>> getMyParts(
            @Authenticated final MemberInfo memberInfo
    );
}
