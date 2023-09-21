package shook.shook.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
