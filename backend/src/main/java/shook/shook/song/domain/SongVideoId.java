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
public class SongVideoId {

    private static final int YOUTUBE_VIDEO_ID_LENGTH = 11;
    private static final String YOUTUBE_VIDEO_PREFIX = "https://youtu.be/%s";

    @Column(name = "video_id", length = 20, nullable = false)
    private String value;

    public SongVideoId(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (StringChecker.isNullOrBlank(value)) {
            throw new SongException.NullOrEmptyVideoIdException();
        }
        if (value.length() != YOUTUBE_VIDEO_ID_LENGTH) {
            throw new SongException.IncorrectVideoIdLengthException(
                Map.of("SongVideoId", value)
            );
        }
    }

    public String convertToVideoUrl() {
        return String.format(YOUTUBE_VIDEO_PREFIX, value);
    }
}
