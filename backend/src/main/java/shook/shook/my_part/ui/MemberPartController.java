package shook.shook.my_part.ui;

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
import shook.shook.my_part.application.MemberPartService;
import shook.shook.my_part.application.dto.MemberPartRegisterRequest;
import shook.shook.my_part.ui.openapi.MemberPartApi;

@RequiredArgsConstructor
@RequestMapping("/songs/{song_id}/member-parts")
@RestController
public class MemberPartController implements MemberPartApi {

    private final MemberPartService memberPartService;

    @PostMapping
    public ResponseEntity<Void> register(
        @PathVariable(name = "song_id") final Long songId,
        @Authenticated final MemberInfo memberInfo,
        @Valid @RequestBody final MemberPartRegisterRequest request
    ) {
        memberPartService.register(songId, memberInfo.getMemberId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
