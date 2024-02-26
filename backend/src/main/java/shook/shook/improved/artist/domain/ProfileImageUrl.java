package shook.shook.improved.artist.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.improved.artist.exception.ArtistException.NullOrEmptyProfileUrlException;
import shook.shook.improved.artist.exception.ArtistException.TooLongProfileUrlException;
import shook.shook.improved.util.StringChecker;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class ProfileImageUrl {

    private static final int MAXIMUM_LENGTH = 65_536;

    @Column(name = "profile_image_url", columnDefinition = "text", nullable = false)
    private String value;

    public ProfileImageUrl(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new NullOrEmptyProfileUrlException();
        }
        if (value.length() > MAXIMUM_LENGTH) {
            throw new TooLongProfileUrlException(
                Map.of("ArtistProfileImageUrl", value)
            );
        }
    }
}
