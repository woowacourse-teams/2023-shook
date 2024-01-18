package shook.shook.legacy.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.legacy.member.exception.MemberException;
import shook.shook.legacy.util.StringChecker;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Email {

    private static final int EMAIL_MAXIMUM_LENGTH = 100;

    @Column(name = "email", length = 100, nullable = false)
    private String value;

    public Email(final String value) {
        validateEmail(value);
        this.value = value;
    }

    private void validateEmail(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new MemberException.NullOrEmptyEmailException();
        }
        if (value.length() > EMAIL_MAXIMUM_LENGTH) {
            throw new MemberException.TooLongEmailException(
                Map.of("Email", value)
            );
        }
    }
}
