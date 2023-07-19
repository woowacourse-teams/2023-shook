package shook.shook.song.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.SongResponse;

@RequiredArgsConstructor
@RequestMapping("/songs/{song_id}")
@RestController
public class SongController {

    private final SongService songService;

    @GetMapping
    public ResponseEntity<SongResponse> showSongById(@PathVariable(name = "song_id") Long songId) {
        final SongResponse response = songService.findById(songId);
        return ResponseEntity.ok(response);
    }
}
