package shook.shook.song.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.ui.argumentresolver.Member;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.song.application.killingpart.KillingPartLikeService;
import shook.shook.song.application.killingpart.dto.KillingPartLikeRequest;

@RequiredArgsConstructor
@RequestMapping("/songs/{song_id}/parts/{killing_part_id}/likes")
@RestController
public class KillingPartLikeController {

    private final KillingPartLikeService killingPartLikeService;

    @PutMapping
    public ResponseEntity<Void> createLikeOnKillingPart(
        @PathVariable(name = "killing_part_id") final Long killingPartId,
        @RequestBody final KillingPartLikeRequest request,
        @Member final MemberInfo memberInfo
    ) {
        killingPartLikeService.updateLikeStatus(killingPartId, memberInfo.getMemberId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
