package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.ManiaDBSearchService;
import shook.shook.song.application.dto.UnregisteredSongSearchResponse;

@RequiredArgsConstructor
@RequestMapping("/songs/unregistered/search")
@RestController
public class UnregisteredSongSearchController {

    private final ManiaDBSearchService maniaDBSearchService;

    @GetMapping
    public ResponseEntity<List<UnregisteredSongSearchResponse>> searchUnregisteredSong(
        final @RequestParam("keyword") String searchWord
    ) {
        final List<UnregisteredSongSearchResponse> songs = maniaDBSearchService.searchSongs(
            searchWord);

        return ResponseEntity.ok(songs);
    }
}
