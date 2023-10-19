package shook.shook.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Search", description = "검색 API")
public interface SearchApi {

    @Operation(
        summary = "가수, 또는 가수와 노래 조회",
        description = "가수를 가나다 순으로 조회한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "가수 정보, (노래 목록) 검색 성공"
    )
    @Parameter(
        name = "type",
        description = "검색 타입, singer&song OR singer",
        required = true
    )
    @Parameter(
        name = "keyword",
        description = "검색 키워드",
        required = true
    )
    @GetMapping("")
    ResponseEntity<List<?>> search(
        @RequestParam(name = "type") final List<String> types,
        @RequestParam(name = "keyword") final String keyword
    );
}
