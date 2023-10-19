package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.ArtistSearchService;
import shook.shook.song.application.dto.ArtistResponse;
import shook.shook.song.application.dto.ArtistWithSongSearchResponse;
import shook.shook.song.ui.openapi.SearchApi;

@RequiredArgsConstructor
@RequestMapping("/search")
@RestController
public class SearchController implements SearchApi {

    private final ArtistSearchService artistSearchService;

    @GetMapping(params = {"keyword", "type=singer,song"})
    public ResponseEntity<List<ArtistWithSongSearchResponse>> searchArtistWithSongByKeyword(
        @RequestParam(name = "type") final List<String> types,
        @RequestParam(name = "keyword") final String keyword) {
        return ResponseEntity.ok(artistSearchService.searchArtistsAndTopSongsByKeyword(keyword));
    }

    @GetMapping(params = {"keyword", "type=singer"})
    public ResponseEntity<List<ArtistResponse>> searchArtistByKeyword(
        @RequestParam(name = "type") final String type,
        @RequestParam(name = "keyword") final String keyword) {
        return ResponseEntity.ok(artistSearchService.searchArtistsByKeyword(keyword));
    }

}
