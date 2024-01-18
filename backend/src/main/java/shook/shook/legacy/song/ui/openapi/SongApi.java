package shook.shook.legacy.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.legacy.song.application.dto.SongResponse;

@Tag(name = "Song API", description = "노래 단일 조회 API")
public interface SongApi {

    @Operation(
        summary = "노래 단일 조회",
        description = "노래 id 과 액세스 토큰을 받아 노래 하나를 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "노래 단일 조회 성공"
    )
    @Parameters({
        @Parameter(
            name = "songId",
            description = "노래 id",
            required = true
        ),
        @Parameter(
            name = "memberInfo",
            description = "회원 정보",
            required = true,
            hidden = true
        )
    })
    @GetMapping("/{song_id}")
    ResponseEntity<SongResponse> findSong(
        @PathVariable("song_id") final Long songId,
        @Authenticated final MemberInfo memberInfo
    );
}
