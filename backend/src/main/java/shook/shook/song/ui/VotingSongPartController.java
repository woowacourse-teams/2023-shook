package shook.shook.song.ui;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.VotingSongPartService;
import shook.shook.song.application.dto.voting_song.VotingSongPartRegisterRequest;

@RequiredArgsConstructor
@RequestMapping("/voting-songs/{voting_song_id}/parts")
@RestController
public class VotingSongPartController {

    private final VotingSongPartService votingSongPartService;

    @PostMapping
    public ResponseEntity<Void> registerPart(
        @PathVariable(name = "voting_song_id") final Long votingSongId,
        @RequestBody final VotingSongPartRegisterRequest request
    ) {
        votingSongPartService.register(votingSongId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
