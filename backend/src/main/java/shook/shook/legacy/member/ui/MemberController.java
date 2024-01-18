package shook.shook.legacy.member.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shook.shook.legacy.auth.ui.argumentresolver.Authenticated;
import shook.shook.legacy.auth.ui.argumentresolver.MemberInfo;
import shook.shook.legacy.member.application.MemberService;
import shook.shook.legacy.member.ui.openapi.MemberApi;

@RequiredArgsConstructor
@RequestMapping("/members/{member_id}")
@RestController
public class MemberController implements MemberApi {

    private final MemberService memberService;

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
        @PathVariable(name = "member_id") final Long memberId,
        @Authenticated final MemberInfo memberInfo
    ) {
        memberService.deleteById(memberId, memberInfo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
