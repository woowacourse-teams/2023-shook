package shook.shook.song.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.song.application.killingpart.KillingPartLikeService;
import shook.shook.song.application.killingpart.dto.KillingPartLikeRequest;

@RequiredArgsConstructor
@RequestMapping("/songs/{song_id}/parts/{killing_part_id}/likes")
@RestController
public class KillingPartLikeController {

    private final KillingPartLikeService killingPartLikeService;

    @PostMapping
    public ResponseEntity<Void> createLikeOnKillingPart(
        @PathVariable(name = "killing_part_id") final Long killingPartId,
        @RequestBody final KillingPartLikeRequest request
        // TODO: 2023-08-13 ArgumentResolver에서 memberId 받아오도록 변경
    ) {
        killingPartLikeService.create(killingPartId, request.getMemberId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLikeOnKillingPart(
        @PathVariable(name = "killing_part_id") final Long killingPartId,
        @RequestBody final KillingPartLikeRequest request
        // TODO: 2023-08-13 ArgumentResolver에서 memberId 받아오도록 변경
    ) {
        killingPartLikeService.delete(killingPartId, request.getMemberId());

        return ResponseEntity.noContent().build();
    }


}
