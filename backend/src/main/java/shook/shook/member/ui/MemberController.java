package shook.shook.member.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.auth.ui.argumentresolver.Authenticated;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.application.MemberService;
import shook.shook.member.ui.openapi.MemberApi;
import shook.shook.song.application.killingpart.KillingPartCommentService;
import shook.shook.song.application.killingpart.KillingPartLikeService;

@RequiredArgsConstructor
@RequestMapping("/members/{member_id}")
@RestController
public class MemberController implements MemberApi {

    private final MemberService memberService;
    private final KillingPartCommentService killingPartCommentService;
    private final KillingPartLikeService killingPartLikeService;

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
        @PathVariable(name = "member_id") final Long memberId,
        @Authenticated final MemberInfo memberInfo
    ) {
        killingPartCommentService.deleteAllByMemberId(memberId);
        killingPartLikeService.deleteAllByIMemberId(memberId);
        memberService.deleteById(memberId, memberInfo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
