package shook.shook.song.ui;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.killingpart.KillingPartCommentService;
import shook.shook.song.application.killingpart.dto.KillingPartCommentRegisterRequest;
import shook.shook.song.application.killingpart.dto.KillingPartCommentResponse;

@RequiredArgsConstructor
@RequestMapping("/songs/{song_id}/parts/{killing_part_id}/comments")
@RestController
public class KillingPartCommentController {

    private final KillingPartCommentService killingPartCommentService;

    @PostMapping
    public ResponseEntity<Void> registerKillingPartComment(
        @PathVariable(name = "killing_part_id") final Long killingPartId,
        @Valid @RequestBody final KillingPartCommentRegisterRequest request
        // TODO: 2023/08/15 memberInfo 추가하기
    ) {
        killingPartCommentService.register(killingPartId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<KillingPartCommentResponse>> findKillingPartComments(
        @PathVariable(name = "killing_part_id") final Long killingPartId
    ) {
        return ResponseEntity.ok(killingPartCommentService.findKillingPartComments(killingPartId));
    }
}
