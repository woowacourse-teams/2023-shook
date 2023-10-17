package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.ArtistSearchService;
import shook.shook.song.application.dto.ArtistResponse;
import shook.shook.song.application.dto.ArtistWithSongSearchResponse;
import shook.shook.song.ui.openapi.ArtistSongSearchApi;

@RequiredArgsConstructor
@RequestMapping("/singers")
@RestController
public class ArtistSongSearchController implements ArtistSongSearchApi {

    private final ArtistSearchService artistSearchService;

    @GetMapping(params = {"name", "search=singer,song"})
    public ResponseEntity<List<ArtistWithSongSearchResponse>> searchArtistWithSongByKeyword(
        @RequestParam(name = "search") final List<String> searchTypes,
        @RequestParam(name = "name") final String name) {
        return ResponseEntity.ok(artistSearchService.searchArtistsAndTopSongsByKeyword(name));
    }

    @GetMapping(params = {"name", "search=singer"})
    public ResponseEntity<List<ArtistResponse>> searchArtistByKeyword(
        @RequestParam(name = "search") final String search,
        @RequestParam(name = "name") final String name) {
        return ResponseEntity.ok(artistSearchService.searchArtistsByKeyword(name));
    }

    @GetMapping("/{artist_id}")
    public ResponseEntity<ArtistWithSongSearchResponse> searchSongsByArtist(
        @PathVariable(name = "artist_id") final Long artistId) {
        return ResponseEntity.ok(artistSearchService.searchAllSongsByArtist(artistId));
    }
}
