package shook.shook.part.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.part.application.PartService;
import shook.shook.part.application.dto.PartRegisterRequest;
import shook.shook.part.application.dto.PartRegisterResponse;

@RequiredArgsConstructor
@RequestMapping("/songs/{song_id}/parts")
@RestController
public class PartController {

    private final PartService partService;

    @PostMapping
    public ResponseEntity<PartRegisterResponse> registerPart(
        @PathVariable(name = "song_id") final Long songId,
        @RequestBody final PartRegisterRequest request
    ) {
        final PartRegisterResponse register = partService.register(songId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(register);
    }
}
