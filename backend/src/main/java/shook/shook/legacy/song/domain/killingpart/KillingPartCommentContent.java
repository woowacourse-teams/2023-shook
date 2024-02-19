package shook.shook.legacy.song.domain.killingpart;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.song.exception.legacy_killingpart.KillingPartCommentException.NullOrEmptyPartCommentException;
import shook.shook.song.exception.legacy_killingpart.KillingPartCommentException.TooLongPartCommentException;
import shook.shook.util.StringChecker;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class KillingPartCommentContent {

    private static final int MAXIMUM_LENGTH = 200;

    @Column(name = "content", length = 200, nullable = false)
    private String value;

    public KillingPartCommentContent(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new NullOrEmptyPartCommentException();
        }
        if (value.length() > MAXIMUM_LENGTH) {
            throw new TooLongPartCommentException(
                Map.of("KillingPartCommentContent", value)
            );
        }
    }
}
