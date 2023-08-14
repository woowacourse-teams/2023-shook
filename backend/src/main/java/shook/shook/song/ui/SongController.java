package shook.shook.song.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.SongResponse;

@RequiredArgsConstructor
@RequestMapping("/songs/{song_id}")
@RestController
public class SongController {

    private final SongService songService;

    @GetMapping
    public ResponseEntity<SongResponse> showSongById(
        @PathVariable(name = "song_id") final Long songId,
        @RequestParam final Long memberId
        // TODO: 2023-08-14 accessToken 으로 변경
    ) {
        final SongResponse response = songService.findByIdAndMemberId(songId, memberId);
        return ResponseEntity.ok(response);
    }
}
