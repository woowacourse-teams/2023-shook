package shook.shook.voting_song.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.voting_song.domain.VotingSong;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class VotingSongSwipeResponse {

    private VotingSongResponse currentSong;
    private List<VotingSongResponse> prevSongs;
    private List<VotingSongResponse> nextSongs;

    public static VotingSongSwipeResponse of(
        final VotingSong currentSong,
        final List<VotingSong> prevSongs,
        final List<VotingSong> nextSongs
    ) {
        final VotingSongResponse currentResponse = VotingSongResponse.from(currentSong);
        final List<VotingSongResponse> prevResponses = prevSongs.stream()
            .map(VotingSongResponse::from)
            .toList();
        final List<VotingSongResponse> nextResponses = nextSongs.stream()
            .map(VotingSongResponse::from)
            .toList();

        return new VotingSongSwipeResponse(currentResponse, prevResponses, nextResponses);
    }
}
