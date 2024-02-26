package shook.shook.improved.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.improved.song.exception.SongException.SongLengthLessThanOneException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class SongLength {

    private static final int MINIMUM_SONG_LENGTH = 0;

    @Column(name = "length", nullable = false)
    private int value;

    public SongLength(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value <= MINIMUM_SONG_LENGTH) {
            throw new SongLengthLessThanOneException(
                Map.of("SongLength", String.valueOf(value))
            );
        }
    }
}
