package shook.shook.legacy.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.legacy.song.application.ArtistSearchService;
import shook.shook.legacy.song.ui.openapi.SearchApi;

@RequiredArgsConstructor
@RequestMapping("/search")
@RestController
public class SearchController implements SearchApi {

    private final ArtistSearchService artistSearchService;

    @GetMapping
    public ResponseEntity<List<?>> search(@RequestParam(name = "type") final List<String> types,
                                          @RequestParam(name = "keyword") final String keyword) {
        if (types.containsAll(List.of("song", "singer"))) {
            return ResponseEntity.ok(artistSearchService.searchArtistsAndTopSongsByKeyword(keyword));
        }
        return ResponseEntity.ok(artistSearchService.searchArtistsByKeyword(keyword));
    }
    // TODO: 2023-10-19 리팩터링: 검색 타입 enum 생성
}
