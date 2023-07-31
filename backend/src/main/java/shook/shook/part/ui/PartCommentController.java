package shook.shook.part.ui;

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
import shook.shook.part.application.PartCommentService;
import shook.shook.part.application.dto.PartCommentRegisterRequest;
import shook.shook.part.application.dto.PartCommentResponse;

@RequiredArgsConstructor
@RequestMapping("/songs/{song_id}/parts/{part_id}/comments")
@RestController
public class PartCommentController {

    private final PartCommentService partCommentService;

    @PostMapping
    public ResponseEntity<Void> registerPartComment(
        @PathVariable(name = "part_id") final Long partId,
        @RequestBody final PartCommentRegisterRequest request
    ) {
        partCommentService.register(partId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<PartCommentResponse>> findPartReplies(
        @PathVariable(name = "part_id") final Long partId
    ) {
        return ResponseEntity.ok().body(partCommentService.findPartReplies(partId));
    }
}
