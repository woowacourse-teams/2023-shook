package shook.shook.song.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        @Valid @RequestBody final SongWithKillingPartsRegisterRequest request
    ) {
        songService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
