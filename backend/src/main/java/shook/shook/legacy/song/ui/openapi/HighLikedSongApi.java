package shook.shook.legacy.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shook.shook.legacy.song.application.killingpart.dto.HighLikedSongResponse;

@Tag(name = "High-Liked Song", description = "좋아요 순 노래 조회 API")
public interface HighLikedSongApi {

    @Operation(
        summary = "좋아요 순 노래 조회",
        description = "킬링파트 좋아요가 많은 순으로 노래를 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "좋아요 순으로 노래 조회 성공"
    )
    @GetMapping
    ResponseEntity<List<HighLikedSongResponse>> showHighLikedSongs();

    @Operation(
        summary = "장르로 좋아요 순 노래 조회",
        description = "해당 장르의 킬링파트 좋아요가 많은 순으로 노래를 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "장르로 좋아요 순으로 노래 조회 성공"
    )
    @Parameter(
        name = "genre",
        description = "장르",
        required = true
    )
    @GetMapping(params = "genre")
    ResponseEntity<List<HighLikedSongResponse>> showHighLikedSongsWithGenre(
        @RequestParam(name = "genre") final String genre
    );
}
