package shook.shook.voting_song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.voting_song.application.VotingSongService;
import shook.shook.voting_song.application.dto.VotingSongResponse;

@RequiredArgsConstructor
@RequestMapping("/voting-songs")
@RestController
public class VotingSongController {

    private final VotingSongService votingSongService;

    @GetMapping
    public ResponseEntity<List<VotingSongResponse>> findAll() {
        final List<VotingSongResponse> allVotingSongs = votingSongService.findAll();

        return ResponseEntity.ok(allVotingSongs);
    }
}
