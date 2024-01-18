package shook.shook.legacy.song.ui.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shook.shook.legacy.song.application.dto.RecentSongCarouselResponse;

@Tag(name = "Carousel Songs", description = "메인페이지 캐러셀 조회 API")
public interface CarouselSongApi {

    @Operation(
        summary = "캐러셀에 들어갈 노래 반환",
        description = "캐러셀에 들어갈 노래 5개를 등록 최신 순 리스트로 반환한다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "최근에 등록된 노래 리스트 조회 성공"
    )
    @Parameter(
        name = "size",
        description = "조회할 개수",
        example = "4"
    )
    @GetMapping
    ResponseEntity<List<RecentSongCarouselResponse>> findRecentSongsForCarousel(
        @RequestParam(name = "size", defaultValue = "5", required = false) final Integer size
    );
}
