package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.dto.SongSwipeResponse;
import shook.shook.song.ui.openapi.SongSwipeApi;

@RequiredArgsConstructor
@RequestMapping("/songs/high-liked/{song_id}")
@RestController
public class SongSwipeController implements SongSwipeApi {

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

    @GetMapping(params = "genre")
    public ResponseEntity<SongSwipeResponse> showSongsWithGenreForSwipe(
        @PathVariable(name = "song_id") final Long songId,
        @RequestParam(name = "genre") final String genre,
        @Authenticated final MemberInfo memberInfo
    ) {
        final SongSwipeResponse response =
            songService.findSongsByGenreForSwipe(songId, genre, memberInfo);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/prev", params = "genre")
    public ResponseEntity<List<SongResponse>> showPrevSongsWithGenre(
        @PathVariable(name = "song_id") final Long songId,
        @RequestParam(name = "genre") final String genre,
        @Authenticated final MemberInfo memberInfo
    ) {
        final List<SongResponse> response =
            songService.findPrevSongsByGenre(songId, genre, memberInfo);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/next", params = "genre")
    public ResponseEntity<List<SongResponse>> showNextSongsWithGenre(
        @PathVariable(name = "song_id") final Long songId,
        @RequestParam(name = "genre") final String genre,
        @Authenticated final MemberInfo memberInfo
    ) {
        final List<SongResponse> response =
            songService.findNextSongsByGenre(songId, genre, memberInfo);

        return ResponseEntity.ok(response);
    }
}
