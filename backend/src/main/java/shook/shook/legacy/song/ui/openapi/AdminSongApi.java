package shook.shook.legacy.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shook.shook.legacy.song.application.dto.SongWithKillingPartsRegisterRequest;

@Tag(name = "Admin Song", description = "파트 수집 중인 노래 API")
public interface AdminSongApi {

    @Operation(
        summary = "파트 수집 중인 노래 등록",
        description = "파트 수집 중인 노래와 노래의 킬링파트 3개를 등록한다."
    )
    @ApiResponse(
        responseCode = "201",
        description = "파트 수집 중인 노래 등록 성공"
    )
    @Parameter(
        description = "파트 수집 중인 노래 등록 요청",
        required = true
    )
    @PostMapping
    ResponseEntity<Void> registerSongWithKillingParts(
        @RequestBody final SongWithKillingPartsRegisterRequest request
    );
}
