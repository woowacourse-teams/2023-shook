package shook.shook.legacy.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.legacy.song.exception.ArtistException;
import shook.shook.util.StringChecker;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class ArtistName {

    private static final int NAME_MAXIMUM_LENGTH = 50;
    private static final String BLANK = "\\s";

    @Column(name = "name", length = 50, nullable = false)
    private String value;

    public ArtistName(final String value) {
        validateName(value);
        this.value = value;
    }

    private void validateName(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new ArtistException.NullOrEmptyNameException();
        }
        if (value.length() > NAME_MAXIMUM_LENGTH) {
            throw new ArtistException.TooLongNameException(
                Map.of("Singer", value)
            );
        }
    }

    public boolean startsWithIgnoringCaseAndWhiteSpace(final String keyword) {
        final String targetKeyword = toLowerCaseRemovingWhiteSpace(keyword);
        if (StringChecker.isNullOrBlank(targetKeyword)) {
            return false;
        }

        return toLowerCaseRemovingWhiteSpace(value)
            .startsWith(toLowerCaseRemovingWhiteSpace(keyword));
    }

    private String toLowerCaseRemovingWhiteSpace(final String word) {
        return removeAllWhiteSpace(word).toLowerCase();
    }

    public boolean endsWithIgnoringCaseAndWhiteSpace(final String keyword) {
        final String targetKeyword = toLowerCaseRemovingWhiteSpace(keyword);
        if (StringChecker.isNullOrBlank(targetKeyword)) {
            return false;
        }

        return toLowerCaseRemovingWhiteSpace(value)
            .endsWith(toLowerCaseRemovingWhiteSpace(keyword));
    }

    private String removeAllWhiteSpace(final String word) {
        return word.replaceAll(BLANK, "");
    }
}
