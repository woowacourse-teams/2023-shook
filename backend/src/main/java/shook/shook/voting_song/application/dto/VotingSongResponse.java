package shook.shook.voting_song.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.voting_song.domain.VotingSong;

@AllArgsConstructor
@Getter
public class VotingSongResponse {

    private final Long id;
    private final String title;
    private final String singer;
    private final int videoLength;
    private final String songVideoId;
    private final String albumCoverUrl;

    public static VotingSongResponse from(final VotingSong song) {
        return new VotingSongResponse(
            song.getId(),
            song.getTitle(),
            song.getSinger(),
            song.getLength(),
            song.getVideoId(),
            song.getAlbumCoverUrl()
        );
    }
}
