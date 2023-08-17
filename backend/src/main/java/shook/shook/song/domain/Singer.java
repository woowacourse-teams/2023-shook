package shook.shook.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.song.exception.SongException;
import shook.shook.util.StringChecker;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Singer {

    private static final int NAME_MAXIMUM_LENGTH = 50;

    @Column(name = "singer", length = 50, nullable = false)
    private String name;

    public Singer(final String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(final String name) {
        if (StringChecker.isNullOrBlank(name)) {
            throw new SongException.NullOrEmptySingerNameException();
        }
        if (name.length() > NAME_MAXIMUM_LENGTH) {
            throw new SongException.TooLongSingerNameException(
                Map.of("Singer", name)
            );
        }
    }
}
