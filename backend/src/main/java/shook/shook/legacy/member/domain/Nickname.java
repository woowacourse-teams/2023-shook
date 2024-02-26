package shook.shook.legacy.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.improved.member.exception.MemberException.NullOrEmptyNicknameException;
import shook.shook.improved.member.exception.MemberException.TooLongNicknameException;
import shook.shook.improved.util.StringChecker;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Nickname {

    private static final int NICKNAME_MAXIMUM_LENGTH = 100;

    @Column(name = "nickname", length = NICKNAME_MAXIMUM_LENGTH, nullable = false)
    private String value;

    public Nickname(final String value) {
        validateNickname(value);
        this.value = value;
    }

    private void validateNickname(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new NullOrEmptyNicknameException();
        }
        if (value.length() > NICKNAME_MAXIMUM_LENGTH) {
            throw new TooLongNicknameException(
                Map.of("Nickname", value)
            );
        }
    }
}
