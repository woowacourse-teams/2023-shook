package shook.shook.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.song.exception.ArtistException;
import shook.shook.util.StringChecker;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Synonym {

    private static final int MAXIMUM_LENGTH = 255;

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
}
