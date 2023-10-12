package shook.shook.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shook.shook.song.application.dto.ArtistResponse;
import shook.shook.song.application.dto.ArtistWithSongSearchResponse;

@Tag(name = "Artist Search", description = "가수 이름 검색 API")
public interface ArtistSongSearchApi {

    @Operation(
        summary = "검색어 입력 시 자동 완성되는 가수의 정보 검색",
        description = "검색어 입력 시, 검색어로 시작하는 가수의 정보를 검색한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "가수 검색 성공"
    )
    @GetMapping(value = "?search=artist", params = {"name"})
    ResponseEntity<List<ArtistResponse>> searchArtistByKeyword(
        @Parameter(name = "search", description = "검색 타입",
            schema = @Schema(enumAsRef = true, allowableValues = {"artist"}))
        @RequestParam(name = "search") final String search,
        @Parameter(
            name = "name",
            description = "검색할 가수 키워드",
            required = true
        )
        @RequestParam(name = "name") final String name
    );

    @Operation(
        summary = "검색 시, 검색 결과 조회",
        description = "검색 시, 검색어로 시작하거나 끝나는 가수와 해당 가수의 TOP3 노래가 조회된다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "가수, TOP3 노래 검색 성공"
    )
    @GetMapping(value = "?search=artist,song", params = {"name"})
    ResponseEntity<List<ArtistWithSongSearchResponse>> searchArtistWithSongByKeyword(
        @Parameter(name = "search", description = "검색 타입",
            schema = @Schema(enumAsRef = true, allowableValues = {"artist", "song"}))
        @RequestParam(name = "search") final List<String> searchTypes,
        @Parameter(
            name = "name",
            description = "검색할 가수 키워드",
            required = true
        )
        @RequestParam(name = "name") final String name
    );

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
