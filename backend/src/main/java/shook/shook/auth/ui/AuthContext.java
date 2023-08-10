package shook.shook.auth.ui;

import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import shook.shook.auth.exception.AuthorizationException;

@RequestScope
@Component
public class AuthContext {

    private Long memberId;

    public void setMemberId(final Long memberId) {
        if (Objects.isNull(memberId) || memberId <= 0) {
            throw new AuthorizationException.AuthContextException();
        }
        this.memberId = memberId;
    }

    public long getMemberId() {
        if (Objects.isNull(memberId)) {
            throw new AuthorizationException.AuthContextException();
        }
        return memberId;
    }
}
