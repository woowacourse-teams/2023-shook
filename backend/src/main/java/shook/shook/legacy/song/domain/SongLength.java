package shook.shook.legacy.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.improved.song.exception.SongException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class SongLength {

    @Column(name = "length", nullable = false)
    private int value;

    public SongLength(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value <= 0) {
            throw new SongException.SongLengthLessThanOneException(
                Map.of("SongLength", String.valueOf(value))
            );
        }
    }
}
