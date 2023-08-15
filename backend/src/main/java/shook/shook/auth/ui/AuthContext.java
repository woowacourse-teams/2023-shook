package shook.shook.auth.ui;

import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import shook.shook.auth.exception.AuthorizationException;

@RequestScope
@Component
public class AuthContext {

    private Long memberId;
    private MemberStatus memberStatus;

    public void setLoginMember(final Long memberId) {
        validateMemberId(memberId);
        this.memberId = memberId;
        this.memberStatus = MemberStatus.MEMBER;
    }

    public void setNotLoginMember() {
        this.memberId = 0L;
        this.memberStatus = MemberStatus.ANONYMOUS;
    }

    private static void validateMemberId(final Long memberId) {
        if (Objects.isNull(memberId) || memberId < 0) {
            throw new AuthorizationException.AuthContextException();
        }
    }

    public boolean isAnonymous() {
        return this.memberStatus == MemberStatus.ANONYMOUS;
    }

    public long getMemberId() {
        if (Objects.isNull(memberId)) {
            throw new AuthorizationException.AuthContextException();
        }
        return memberId;
    }

    public MemberStatus getMemberStatus() {
        if (Objects.isNull(memberStatus)) {
            throw new AuthorizationException.AuthContextException();
        }
        return memberStatus;
    }
}
