package shook.shook.song.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.song.application.MyPageService;
import shook.shook.song.application.dto.LikedKillingPartResponse;
import shook.shook.song.ui.openapi.MyPageApi;

@RequiredArgsConstructor
@RestController
@RequestMapping("/my-page")
public class MyPageController implements MyPageApi {

    private final MyPageService myPageService;

    @GetMapping
    public ResponseEntity<List<LikedKillingPartResponse>> getMemberLikedKillingParts(
        @Authenticated final MemberInfo memberInfo
    ) {
        return ResponseEntity.ok(myPageService.findLikedKillingPartByMemberId(memberInfo));
    }
}
