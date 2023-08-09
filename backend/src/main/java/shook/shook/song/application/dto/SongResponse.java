package shook.shook.song.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Song;

@AllArgsConstructor
@Getter
public class SongResponse {

    private final Long id;
    private final String title;
    private final String singer;
    private final int videoLength;
    private final String songVideoUrl;
    private final String albumCoverUrl;
    private final List<KillingPartResponse> killingParts;

    public static SongResponse from(final Song song) {
        return new SongResponse(
            song.getId(),
            song.getTitle(),
            song.getSinger(),
            song.getLength(),
            song.getVideoUrl(),
            song.getAlbumCoverUrl(),
            song.getKillingParts().stream()
                .map((killingPart) -> KillingPartResponse.of(song, killingPart))
                .toList()
        );
    }
}
