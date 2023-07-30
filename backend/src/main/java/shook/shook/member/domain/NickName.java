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
public class NickName {

    private static final int NICK_NAME_MAXIMUM_LENGTH = 100;

    @Column(name = "nick_name", length = NICK_NAME_MAXIMUM_LENGTH, nullable = false)
    private String value;

    public NickName(final String value) {
        validateNickName(value);
        this.value = value;
    }

    private void validateNickName(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new MemberException.NullOrEmptyNickNameException();
        }
        if (value.length() > NICK_NAME_MAXIMUM_LENGTH) {
            throw new MemberException.TooLongNickNameException();
        }
    }
}
