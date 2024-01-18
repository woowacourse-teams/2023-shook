package shook.shook.legacy.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shook.shook.legacy.song.application.dto.ArtistWithSongSearchResponse;

@Tag(name = "Artist", description = "가수 API")
public interface ArtistApi {

    @Operation(
        summary = "특정 가수의 모든 노래 조회",
        description = "가수의 모든 노래를 좋아요 순으로 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "가수 정보, 노래 목록 검색 성공"
    )
    @Parameter(
        name = "artist_id",
        description = "가수 id",
        required = true
    )
    @GetMapping("/{artist_id}")
    ResponseEntity<ArtistWithSongSearchResponse> searchSongsByArtist(
        @PathVariable(name = "artist_id") final Long artistId
    );
}
