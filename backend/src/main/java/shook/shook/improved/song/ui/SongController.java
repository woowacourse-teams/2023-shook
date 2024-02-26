package shook.shook.improved.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.improved.song.application.SongService;
import shook.shook.improved.song.application.dto.SongHighScoredResponse;
import shook.shook.improved.song.application.dto.SongRegisterRequest;

@RequiredArgsConstructor
@RequestMapping("/songs")
@RestController
public class SongController {

    private final SongService songService;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody SongRegisterRequest songRegisterRequest) {
        songService.register(songRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<SongHighScoredResponse>> showHighScoredSongs() {
        final List<SongHighScoredResponse> response = songService.getHighScoredSong();

        return ResponseEntity.ok(response);
    }

    @GetMapping(params = "genre")
    public ResponseEntity<List<SongHighScoredResponse>> showHighScoredSongsWithGenre(
        @RequestParam(name = "genre") final String genre
    ) {
        final List<SongHighScoredResponse> response =
            songService.findSongsByGenre(genre);

        return ResponseEntity.ok(response);
    }
}
