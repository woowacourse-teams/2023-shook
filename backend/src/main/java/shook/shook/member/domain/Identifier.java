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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class Identifier {

    private static final int IDENTIFIER_MAXIMUM_LENGTH = 100;

    @Column(name = "identifier", length = 100, nullable = false)
    private String value;

    public Identifier(final String value) {
        validateIdentifier(value);
        this.value = value;
    }

    private void validateIdentifier(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new MemberException.NullOrEmptyEmailException();
        }
        if (value.length() > IDENTIFIER_MAXIMUM_LENGTH) {
            throw new MemberException.TooLongIdentifierException(
                Map.of("Identifier", value)
            );
        }
    }
}
