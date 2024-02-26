package shook.shook.legacy.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.improved.song.exception.SongException;
import shook.shook.improved.util.StringChecker;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class AlbumCoverUrl {

    private static final int MAXIMUM_LENGTH = 65_536;

    @Column(name = "album_cover_url", columnDefinition = "text", nullable = false)
    private String value;

    public AlbumCoverUrl(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new SongException.NullOrEmptyImageUrlException();
        }
        if (value.length() > MAXIMUM_LENGTH) {
            throw new SongException.TooLongImageUrlException(
                Map.of("AlbumCoverUrl", value)
            );
        }
    }
}
