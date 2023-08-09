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
    private final Long id;
    private final Integer rank;
    private final Integer voteCount;
    private final Integer start;
    private final Integer end;
    private final String partVideoUrl;

    public static KillingPartResponse of(final Song song, final Part part) {
        final int rank = song.getRank(part);
        final int voteCount = part.getVoteCount();
        final int startSecond = part.getStartSecond();
        final String partVideoUrl = song.getPartVideoUrl(part);
        return new KillingPartResponse(
            true,
            part.getId(),
            rank,
            voteCount,
            startSecond,
            part.getEndSecond(),
            partVideoUrl
        );
    }

    public static KillingPartResponse empty() {
        return new KillingPartResponse(false, null, null, null, null, null, null);
    }
}
