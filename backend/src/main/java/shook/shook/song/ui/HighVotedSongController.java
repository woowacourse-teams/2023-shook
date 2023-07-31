package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.HighVotedSongResponse;

@RequiredArgsConstructor
@RequestMapping("/songs/high-voted")
@RestController
public class HighVotedSongController {

    private final SongService songService;

    @GetMapping
    public ResponseEntity<List<HighVotedSongResponse>> showHighVotedSong() {
        final List<HighVotedSongResponse> response = songService.findHighVotedSongs();

        return ResponseEntity.ok().body(response);
    }
}
