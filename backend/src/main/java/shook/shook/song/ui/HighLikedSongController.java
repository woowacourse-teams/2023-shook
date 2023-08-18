package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.SongService;
import shook.shook.song.application.killingpart.dto.HighLikedSongResponse;
import shook.shook.song.ui.openapi.HighLikedSongApi;

@RequiredArgsConstructor
@RequestMapping("/songs/high-liked")
@RestController
public class HighLikedSongController implements HighLikedSongApi {

    private final SongService songService;

    @GetMapping
    public ResponseEntity<List<HighLikedSongResponse>> showHighLikedSongs() {
        final List<HighLikedSongResponse> response = songService.showHighLikedSongs();

        return ResponseEntity.ok(response);
    }
}
