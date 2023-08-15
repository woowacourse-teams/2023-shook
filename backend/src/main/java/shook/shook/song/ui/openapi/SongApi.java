package shook.shook.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shook.shook.song.application.dto.SongResponse;

@Tag(name = "Song", description = "노래 조회 API")
public interface SongApi {

    @Operation(
        summary = "노래 조회",
        description = "노래 하나를 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "노래 조회 성공"
    )
    @GetMapping
    ResponseEntity<SongResponse> showSongById(
        @PathVariable(name = "song_id") final Long songId,
        @RequestParam final Long memberId
        // TODO: 2023-08-14 accessToken 으로 변경
    );
}
