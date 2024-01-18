package shook.shook.legacy.voting_song.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.legacy.voting_song.application.VotingSongPartService;
import shook.shook.legacy.voting_song.application.dto.VotingSongPartRegisterRequest;
import shook.shook.legacy.voting_song.ui.openapi.VotingSongPartApi;

@RequiredArgsConstructor
@RequestMapping("/voting-songs/{voting_song_id}/parts")
@RestController
public class VotingSongPartController implements VotingSongPartApi {

    private final VotingSongPartService votingSongPartService;

    @PostMapping
    public ResponseEntity<Void> registerPart(@Authenticated final MemberInfo memberInfo,
                                             @PathVariable(name = "voting_song_id") final Long votingSongId,
                                             @Valid @RequestBody final VotingSongPartRegisterRequest request) {
        final boolean memberPartDuplication =
            votingSongPartService.registerAndReturnMemberPartDuplication(memberInfo, votingSongId, request);

        if (memberPartDuplication) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
