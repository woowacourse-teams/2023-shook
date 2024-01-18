package shook.shook.legacy.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.legacy.song.exception.SongException;
import shook.shook.legacy.util.StringChecker;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class SongTitle {

    private static final int MAXIMUM_LENGTH = 100;

    @Column(name = "title", length = 100, nullable = false)
    private String value;

    public SongTitle(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new SongException.NullOrEmptyTitleException();
        }
        if (value.length() > MAXIMUM_LENGTH) {
            throw new SongException.TooLongTitleException(
                Map.of("SongTitle", value)
            );
        }
    }
}
