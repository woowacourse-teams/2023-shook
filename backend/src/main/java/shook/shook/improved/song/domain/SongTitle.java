package shook.shook.improved.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.improved.song.exception.SongException.NullOrEmptyTitleException;
import shook.shook.improved.song.exception.SongException.TooLongTitleException;
import shook.shook.improved.util.StringChecker;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
            throw new NullOrEmptyTitleException();
        }
        if (value.length() > MAXIMUM_LENGTH) {
            throw new TooLongTitleException(
                Map.of("SongTitle", value)
            );
        }
    }
}
