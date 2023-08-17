package shook.shook.song.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LikedKillingPartResponse {

    private final Long songId;
    private final String title;
    private final String singer;
    private final String albumCoverUrl;
    private final Long partId;
    private final int start;
    private final int end;

    public static LikedKillingPartResponse of(final Song song, final KillingPart killingPart) {
        return new LikedKillingPartResponse(
            song.getId(),
            song.getTitle(),
            song.getSinger(),
            song.getAlbumCoverUrl(),
            killingPart.getId(),
            killingPart.getStartSecond(),
            killingPart.getEndSecond()
        );
    }
}
