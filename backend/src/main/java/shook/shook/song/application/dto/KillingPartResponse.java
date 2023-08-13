package shook.shook.song.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class KillingPartResponse {

    private final Long id;
    private final int rank;
    private final long likeCount;
    private final int start;
    private final int end;
    private final String partVideoUrl;
    private final int partLength;

    public static KillingPartResponse of(final Song song, final KillingPart killingPart, final int rank) {
        return new KillingPartResponse(
            killingPart.getId(),
            rank,
            killingPart.getLikeCount(),
            killingPart.getStartSecond(),
            killingPart.getEndSecond(),
            song.getPartVideoUrl(killingPart),
            killingPart.getLength()
        );
    }
}
