package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.song.application.KillingPartService;
import shook.shook.song.application.dto.LikedKillingPartResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/my-page")
public class MyPageController {

    private final KillingPartService killingPartService;

    @GetMapping
    public ResponseEntity<List<LikedKillingPartResponse>> getMemberLikedKillingParts(
        @Authenticated MemberInfo memberInfo
    ) {
        return ResponseEntity.ok(killingPartService.findLikedKillingPartByMemberId(memberInfo));
    }
}
