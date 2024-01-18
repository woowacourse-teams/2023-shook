package shook.shook.legacy.song.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.legacy.song.application.ArtistSearchService;
import shook.shook.legacy.song.application.dto.ArtistWithSongSearchResponse;
import shook.shook.legacy.song.ui.openapi.ArtistApi;

@RequiredArgsConstructor
@RequestMapping("/singers")
@RestController
public class ArtistController implements ArtistApi {

    private final ArtistSearchService artistSearchService;

    @GetMapping("/{artist_id}")
    public ResponseEntity<ArtistWithSongSearchResponse> searchSongsByArtist(
        @PathVariable(name = "artist_id") final Long artistId) {
        return ResponseEntity.ok(artistSearchService.searchAllSongsByArtist(artistId));
    }

    @PutMapping("/synonyms")
    public ResponseEntity<Void> updateArtistSynonym() {
        artistSearchService.updateArtistSynonymFromDatabase();

        return ResponseEntity.ok().build();
    }
}
