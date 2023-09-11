package shook.shook.song.ui;

import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.SongWithKillingPartsRegisterRequest;
import shook.shook.song.ui.openapi.AdminSongApi;

@RequiredArgsConstructor
@RequestMapping("/songs")
@RestController
public class AdminSongController implements AdminSongApi {

    private final SongService songService;

    @PostMapping
    public ResponseEntity<Void> registerSongWithKillingParts(
        @Valid @RequestBody final SongWithKillingPartsRegisterRequest request
    ) {
        final Long registeredSongId = songService.register(request);

        return ResponseEntity.created(URI.create("/songs/" + registeredSongId)).build();
    }

    @PostMapping("/file")
    public ResponseEntity<Void> registerSongWithExcelFile(
        @RequestParam("file") MultipartFile excelFile
    ) {
        songService.saveSongsFromExcelFile(excelFile);

        return ResponseEntity.ok().build();
    }
}
