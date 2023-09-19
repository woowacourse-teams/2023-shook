package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.dto.SongSwipeResponse;
import shook.shook.song.ui.openapi.SongApi;

@RequiredArgsConstructor
@RequestMapping("/songs/{song_id}")
@RestController
public class SongController implements SongApi {

    private final SongService songService;

    @GetMapping
    public ResponseEntity<SongSwipeResponse> showSongById(
        @PathVariable(name = "song_id") final Long songId,
        @Authenticated final MemberInfo memberInfo
    ) {
        final SongSwipeResponse response =
            songService.findSongByIdForFirstSwipe(songId, memberInfo);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/prev")
    public ResponseEntity<List<SongResponse>> showSongsBeforeSongWithId(
        @PathVariable(name = "song_id") final Long songId,
        @Authenticated final MemberInfo memberInfo
    ) {
        final List<SongResponse> response =
            songService.findSongByIdForBeforeSwipe(songId, memberInfo);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/next")
    public ResponseEntity<List<SongResponse>> showSongsAfterSongWithId(
        @PathVariable(name = "song_id") final Long songId,
        @Authenticated final MemberInfo memberInfo
    ) {
        final List<SongResponse> response =
            songService.findSongByIdForAfterSwipe(songId, memberInfo);

        return ResponseEntity.ok(response);
    }
}
