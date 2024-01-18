package shook.shook.legacy.auth.ui.argumentresolver;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.legacy.auth.ui.Authority;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberInfo {

    private long memberId;
    private Authority authority;
}
