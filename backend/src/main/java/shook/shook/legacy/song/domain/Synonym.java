package shook.shook.legacy.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.artist.exception.ArtistException;
import shook.shook.util.StringChecker;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Synonym {

    private static final int MAXIMUM_LENGTH = 255;
    private static final String BLANK = "\\s";

    @Column(name = "synonym", nullable = false)
    private String value;

    public Synonym(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new ArtistException.NullOrEmptySynonymException();
        }
        if (value.length() > MAXIMUM_LENGTH) {
            throw new ArtistException.TooLongSynonymException(
                Map.of("ArtistSynonym", value)
            );
        }
    }

    public boolean startsWithIgnoringCaseAndWhiteSpace(final String keyword) {
        final String targetKeyword = toLowerCaseRemovingWhiteSpace(keyword);
        if (StringChecker.isNullOrBlank(targetKeyword)) {
            return false;
        }

        return toLowerCaseRemovingWhiteSpace(value)
            .startsWith(targetKeyword);
    }

    public boolean endsWithIgnoringCaseAndWhiteSpace(final String keyword) {
        final String targetKeyword = toLowerCaseRemovingWhiteSpace(keyword);
        if (StringChecker.isNullOrBlank(targetKeyword)) {
            return false;
        }

        return toLowerCaseRemovingWhiteSpace(value)
            .endsWith(toLowerCaseRemovingWhiteSpace(targetKeyword));
    }

    private String toLowerCaseRemovingWhiteSpace(final String word) {
        return removeAllWhiteSpace(word).toLowerCase();
    }

    private String removeAllWhiteSpace(final String word) {
        return word.replaceAll(BLANK, "");
    }
}
