package shook.shook.voting_song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.voting_song.application.dto.VotingSongPartRegisterRequest;

@Tag(name = "VotingSongPart", description = "파트 수집 중인 노래의 파트 API")
public interface VotingSongPartApi {

    @Operation(
        summary = "파트 수집 중인 노래에 파트 등록",
        description = "파트 수집 중인 노래에 파트를 등록한다."
    )
    @ApiResponse(
        responseCode = "201",
        description = "파트 수집 중인 노래에 파트 등록 성공"
    )
    @Parameter(
        name = "voting_song_id",
        description = "파트 수집 중인 노래의 id",
        required = true
    )
    @Parameter(
        name = "memberInfo",
        description = "토큰을 파싱해서 얻은 회원 정보",
        required = true,
        hidden = true
    )
    @PostMapping
    ResponseEntity<Void> registerPart(
        @Authenticated final MemberInfo memberInfo,
        @PathVariable(name = "voting_song_id") final Long votingSongId,
        @Valid @RequestBody final VotingSongPartRegisterRequest request
    );
}
