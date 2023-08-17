package shook.shook.voting_song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.voting_song.application.VotingSongService;
import shook.shook.voting_song.application.dto.VotingSongResponse;
import shook.shook.voting_song.application.dto.VotingSongSwipeResponse;

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

    //findById 라는 메서드가 pk를 기준으로 한개를 찾아오는 메서드이다 보니 All을 붙여서 여러개를 반환한다는 걸 나타내보면 어떨까요??
    @GetMapping("/{voting_song_id}")
    public ResponseEntity<VotingSongSwipeResponse> findAllForSwipeById(
        @PathVariable("voting_song_id") final Long votingSongId
    ) {
        final VotingSongSwipeResponse swipeResponse =
            votingSongService.findAllForSwipeById(votingSongId);

        return ResponseEntity.ok(swipeResponse);
    }
}
