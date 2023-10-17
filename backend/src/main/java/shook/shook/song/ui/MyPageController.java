package shook.shook.song.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.song.application.MyPageService;
import shook.shook.song.application.dto.LikedKillingPartResponse;
import shook.shook.song.application.dto.MyPartsResponse;
import shook.shook.song.ui.openapi.MyPageApi;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/my-page")
public class MyPageController implements MyPageApi {

    private final MyPageService myPageService;

    @GetMapping("/like-parts")
    public ResponseEntity<List<LikedKillingPartResponse>> getMemberLikedKillingParts(
            @Authenticated final MemberInfo memberInfo
    ) {
        return ResponseEntity.ok(myPageService.findLikedKillingPartByMemberId(memberInfo));
    }

    @GetMapping("/my-parts")
    public ResponseEntity<List<MyPartsResponse>> getMyParts(
            @Authenticated final MemberInfo memberInfo
    ) {
        return ResponseEntity.ok(myPageService.findMyPartByMemberId(memberInfo.getMemberId()));
    }
}
