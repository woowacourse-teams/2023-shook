package shook.shook.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
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

    private static final int NICKNAME_MAXIMUM_LENGTH = 20;

    @Column(name = "nickname", length = NICKNAME_MAXIMUM_LENGTH, nullable = false)
    private String value;

    public Nickname(final String value) {
        validateNickname(value);
        this.value = value;
    }

    private void validateNickname(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new MemberException.NullOrEmptyNicknameException();
        }
        if (value.length() > NICKNAME_MAXIMUM_LENGTH) {
            throw new MemberException.TooLongNicknameException(
                Map.of("Nickname", value)
            );
        }
    }
}
