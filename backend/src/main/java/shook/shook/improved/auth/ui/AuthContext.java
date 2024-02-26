package shook.shook.improved.auth.ui;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public class AuthContext {

    private static final long ANONYMOUS_ID = 0L;

    private Long memberId = ANONYMOUS_ID;
    private Authority authority = Authority.ANONYMOUS;

    public void setAuthenticatedMember(final Long memberId) {
        this.memberId = memberId;
        this.authority = Authority.MEMBER;
    }

    public boolean isAnonymous() {
        return authority.isAnonymous();
    }

    public long getMemberId() {
        return memberId;
    }

    public Authority getAuthority() {
        return authority;
    }
}

