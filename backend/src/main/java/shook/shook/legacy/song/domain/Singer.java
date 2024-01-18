package shook.shook.legacy.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.legacy.song.exception.ArtistException;
import shook.shook.legacy.util.StringChecker;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Singer {
    // TODO: 2023-10-09 데이터 옮긴 후 Song에 있는 해당 컬럼을,, 날려야 하나..?

    private static final int NAME_MAXIMUM_LENGTH = 50;

    @Column(name = "singer", length = 50, nullable = false)
    private String name;

    public Singer(final String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(final String name) {
        if (StringChecker.isNullOrBlank(name)) {
            throw new ArtistException.NullOrEmptyNameException();
        }
        if (name.length() > NAME_MAXIMUM_LENGTH) {
            throw new ArtistException.TooLongNameException(
                Map.of("Singer", name)
            );
        }
    }
}
