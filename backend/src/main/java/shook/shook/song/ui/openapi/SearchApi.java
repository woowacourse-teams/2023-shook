package shook.shook.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shook.shook.song.application.dto.ArtistResponse;
import shook.shook.song.application.dto.ArtistWithSongSearchResponse;

@Tag(name = "Singer Search", description = "가수 이름 검색 API")
public interface SearchApi {

    @Operation(
        summary = "검색어 입력 시 자동 완성되는 가수의 정보 검색",
        description = "검색어 입력 시, 검색어로 시작하는 가수의 정보를 검색한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "가수 검색 성공"
    )
    @GetMapping(value = "?type=singer", params = {"keyword"})
    ResponseEntity<List<ArtistResponse>> searchArtistByKeyword(
        @Parameter(name = "type", description = "검색 타입",
            schema = @Schema(enumAsRef = true, allowableValues = {"singer"}))
        @RequestParam(name = "type") final String type,
        @Parameter(
            name = "keyword",
            description = "검색할 가수 키워드",
            required = true
        )
        @RequestParam(name = "keyword") final String keyword
    );

    @Operation(
        summary = "검색 시, 검색 결과 조회",
        description = "검색 시, 검색어로 시작하거나 끝나는 가수와 해당 가수의 TOP3 노래가 조회된다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "가수, TOP3 노래 검색 성공"
    )
    @GetMapping(value = "?type=singer,song", params = {"keyword"})
    ResponseEntity<List<ArtistWithSongSearchResponse>> searchArtistWithSongByKeyword(
        @Parameter(name = "type", description = "검색 타입",
            schema = @Schema(enumAsRef = true, allowableValues = {"singer", "song"}))
        @RequestParam(name = "type") final List<String> types,
        @Parameter(
            name = "keyword",
            description = "검색할 가수 키워드",
            required = true
        )
        @RequestParam(name = "keyword") final String keyword
    );
}
