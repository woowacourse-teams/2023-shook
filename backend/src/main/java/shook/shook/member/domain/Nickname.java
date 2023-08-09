package shook.shook.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.member.exception.MemberException;
import shook.shook.util.StringChecker;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Nickname {

    private static final int NICK_NAME_MAXIMUM_LENGTH = 100;

    @Column(name = "nickname", length = NICK_NAME_MAXIMUM_LENGTH, nullable = false)
    private String value;

    public Nickname(final String value) {
        validateNickname(value);
        this.value = value;
    }

    private void validateNickname(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new MemberException.NullOrEmptyNicknameException();
        }
        if (value.length() > NICK_NAME_MAXIMUM_LENGTH) {
            throw new MemberException.TooLongNicknameException();
        }
    }
}
