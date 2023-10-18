package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.SongService;
import shook.shook.song.application.dto.RecentSongCarouselResponse;
import shook.shook.song.ui.openapi.CarouselSongApi;

@RequiredArgsConstructor
@RequestMapping("/songs/recent")
@RestController
public class CarouselSongController implements CarouselSongApi {

    private final SongService songService;

    @GetMapping
    public ResponseEntity<List<RecentSongCarouselResponse>> findRecentSongsForCarousel(
        @RequestParam(name = "size", defaultValue = "5", required = false) final Integer size
    ) {
        final List<RecentSongCarouselResponse> responses = songService.findRecentRegisteredSongsForCarousel(size);

        return ResponseEntity.ok(responses);
    }
}
