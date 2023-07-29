package shook.shook.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
    private static final Pattern EMAIL_FORM = Pattern.compile(
        "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    public Email(final String email) {
        validateEmail(email);
        this.email = email;
    }

    private void validateEmail(final String email) {
        if (StringChecker.isNullOrBlank(email)) {
            throw new MemberException.NullOrEmptyEmailException();
        }
        if (email.length() > EMAIL_MAXIMUM_LENGTH) {
            throw new MemberException.TooLongEmailException();
        }
        Matcher matcher = EMAIL_FORM.matcher(email);
        if (!matcher.matches()) {
            throw new MemberException.InValidEmailFormException();
        }
    }
}
