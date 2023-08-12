package shook.shook.song.ui;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.SongWithKillingPartsRegisterRequest;

@RequiredArgsConstructor
@RequestMapping("/songs")
@RestController
public class AdminSongController {

    private final SongService songService;
    
    @PostMapping
    public ResponseEntity<Void> registerSongWithKillingParts(
        @RequestBody final SongWithKillingPartsRegisterRequest request
    ) {
        final long savedSongId = songService.register(request);

        return ResponseEntity.created(URI.create("/" + savedSongId)).build();
    }
}
