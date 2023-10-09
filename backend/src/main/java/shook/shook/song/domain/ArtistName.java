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
public class ArtistName {

    private static final int NAME_MAXIMUM_LENGTH = 50;

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
}
