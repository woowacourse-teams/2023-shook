package shook.shook.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
public class SongImageUrl {

    private static final int MAXIMUM_LENGTH = 65_536;

    @Column(name = "image_url", columnDefinition = "text", nullable = false)
    private String value;

    public SongImageUrl(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new SongException.NullOrEmptyImageUrlException();
        }
        if (value.length() > MAXIMUM_LENGTH) {
            throw new SongException.TooLongImageUrlException();
        }
    }
}
