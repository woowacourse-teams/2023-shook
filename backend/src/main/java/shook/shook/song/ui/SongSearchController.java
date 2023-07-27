package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.SearchedSongResponse;

@RequiredArgsConstructor
@RequestMapping("/songs")
@RestController
public class SongSearchController {

    private final SongService songService;

    @GetMapping(params = {"singer"})
    public ResponseEntity<List<SearchedSongResponse>> searchSongsBySinger(
        @RequestParam String singer) {
        final List<SearchedSongResponse> responses = songService.findAllBySinger(singer);

        return ResponseEntity.ok(responses);
    }
}
