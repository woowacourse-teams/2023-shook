package shook.shook.legacy.song.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.legacy.song.application.SongService;
import shook.shook.legacy.song.application.dto.SongResponse;
import shook.shook.legacy.song.ui.openapi.SongApi;

@RequiredArgsConstructor
@RequestMapping("/songs")
@RestController
public class SongController implements SongApi {

    private final SongService songService;

    @GetMapping("/{song_id}")
    public ResponseEntity<SongResponse> findSong(
        @PathVariable("song_id") final Long songId,
        @Authenticated final MemberInfo memberInfo
    ) {
        return ResponseEntity.ok(songService.findSongById(songId, memberInfo));
    }
}
