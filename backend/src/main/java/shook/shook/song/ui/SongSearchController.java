package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.SongSearchService;
import shook.shook.song.application.dto.SearchedSongResponse;

@RequiredArgsConstructor
@RequestMapping("/songs")
@RestController
public class SongSearchController {

    private final SongSearchService songSearchService;

    @GetMapping
    public ResponseEntity<List<SearchedSongResponse>> searchSongsByTitleAndSinger(
        @RequestParam(required = false) final String singer,
        @RequestParam(required = false) final String title
    ) {
        final List<SearchedSongResponse> responses =
            songSearchService.findAllBySingerAndTitle(singer, title);

        return ResponseEntity.ok(responses);
    }
}
