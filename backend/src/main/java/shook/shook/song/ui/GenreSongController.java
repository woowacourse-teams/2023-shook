package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.GenreSongResponse;

@RequiredArgsConstructor
@RequestMapping("/songs")
@RestController
public class GenreSongController {

    private final SongService songService;

    @GetMapping("/genre")
    public ResponseEntity<List<GenreSongResponse>> showSongsWithGenre(
        @RequestParam(name = "type") final String genre,
        @Authenticated final MemberInfo memberInfo
    ) {
        final List<GenreSongResponse> response =
            songService.findSongsByGenre(genre, memberInfo);

        return ResponseEntity.ok(response);
    }
}
