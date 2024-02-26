package shook.shook.improved.song.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.improved.song.exception.SongException.IncorrectVideoIdLengthException;
import shook.shook.improved.song.exception.SongException.NullOrEmptyVideoIdException;
import shook.shook.improved.util.StringChecker;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
            throw new NullOrEmptyVideoIdException();
        }
        if (value.length() != YOUTUBE_VIDEO_ID_LENGTH) {
            throw new IncorrectVideoIdLengthException(
                Map.of("SongVideoId", value)
            );
        }
    }

    public String convertToVideoUrl() {
        return String.format(YOUTUBE_VIDEO_PREFIX, value);
    }
}

