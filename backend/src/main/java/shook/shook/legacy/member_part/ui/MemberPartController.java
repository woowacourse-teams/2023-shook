package shook.shook.legacy.member_part.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.improved.auth.ui.argumentresolver.Authenticated;
import shook.shook.improved.auth.ui.argumentresolver.MemberInfo;
import shook.shook.legacy.member_part.application.MemberPartService;
import shook.shook.legacy.member_part.application.dto.MemberPartRegisterRequest;
import shook.shook.legacy.member_part.ui.openapi.MemberPartApi;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class MemberPartController implements MemberPartApi {

    private final MemberPartService memberPartService;

    @PostMapping("/songs/{song_id}/member-parts")
    public ResponseEntity<Void> register(
        @PathVariable(name = "song_id") final Long songId,
        @Authenticated final MemberInfo memberInfo,
        @Valid @RequestBody final MemberPartRegisterRequest request
    ) {
        memberPartService.register(songId, memberInfo.getMemberId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/member-parts/{member_part_id}")
    public ResponseEntity<Void> delete(
        @PathVariable(name = "member_part_id") final Long memberPartId,
        @Authenticated final MemberInfo memberInfo
    ) {
        memberPartService.delete(memberInfo.getMemberId(), memberPartId);

        return ResponseEntity.noContent().build();
    }
}
