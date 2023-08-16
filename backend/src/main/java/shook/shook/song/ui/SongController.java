package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.dto.SongSwipeResponse;

@RequiredArgsConstructor
@RequestMapping("/songs/{song_id}")
@RestController
public class SongController {

    private final SongService songService;

    @GetMapping
    public ResponseEntity<SongSwipeResponse> showSongById(
        @PathVariable(name = "song_id") final Long songId,
        @RequestParam final Long memberId
        // TODO: 2023-08-14 accessToken 으로 변경
    ) {
        final SongSwipeResponse response =
            songService.findSongByIdForFirstSwipe(songId, memberId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/before")
    public ResponseEntity<List<SongResponse>> showSongsBeforeSongWithId(
        @PathVariable(name = "song_id") final Long songId,
        @RequestParam final Long memberId
    ) {
        final List<SongResponse> response =
            songService.findSongByIdForBeforeSwipe(songId, memberId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/after")
    public ResponseEntity<List<SongResponse>> showSongsAfterSongWithId(
        @PathVariable(name = "song_id") final Long songId,
        @RequestParam final Long memberId
    ) {
        final List<SongResponse> response =
            songService.findSongByIdForAfterSwipe(songId, memberId);

        return ResponseEntity.ok(response);
    }
}
