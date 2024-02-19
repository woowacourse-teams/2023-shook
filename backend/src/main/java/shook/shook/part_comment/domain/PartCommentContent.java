package shook.shook.part_comment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.part_comment.exception.PartCommentException;
import shook.shook.util.StringChecker;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PartCommentContent {

    private static final int MAXIMUM_LENGTH = 200;

    @Column(name = "content", length = 200, nullable = false)
    private String value;

    public PartCommentContent(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new PartCommentException.NullOrEmptyPartCommentException();
        }
        if (value.length() > MAXIMUM_LENGTH) {
            throw new PartCommentException.TooLongPartCommentException(
                Map.of("PartCommentContent", value)
            );
        }
    }
}
