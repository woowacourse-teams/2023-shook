package shook.shook.song.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.part.domain.Part;
import shook.shook.song.domain.Song;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class KillingPartResponse {

    private final boolean exist;
    private final Integer rank;
    private final Integer start;
    private final Integer end;
    private final String partVideoUrl;

    public static KillingPartResponse of(final Song song, final Part part) {
        final int startSecond = part.getStartSecond();
        final int rank = song.getRank(part);
        final String partVideoUrl = song.getPartVideoUrl(part);
        return new KillingPartResponse(
            true,
            rank,
            startSecond,
            part.getEndSecond(),
            partVideoUrl
        );
    }

    public static KillingPartResponse empty() {
        return new KillingPartResponse(false, null, null, null, null);
    }
}
