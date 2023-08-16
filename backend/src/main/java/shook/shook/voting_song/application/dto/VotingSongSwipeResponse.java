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
    private List<VotingSongResponse> beforeSongs;
    private List<VotingSongResponse> afterSongs;

    public static VotingSongSwipeResponse of(
        final List<VotingSong> songs,
        final VotingSong currentSong
    ) {
        final int votingSongIndex = songs.indexOf(currentSong);
        final List<VotingSong> beforeSongs = songs.subList(0, votingSongIndex);
        final List<VotingSong> afterSongs = songs.subList(votingSongIndex + 1, songs.size());

        final VotingSongResponse currentResponse = VotingSongResponse.from(currentSong);
        final List<VotingSongResponse> beforeResponses = beforeSongs.stream()
            .map(VotingSongResponse::from)
            .toList();
        final List<VotingSongResponse> afterResponses = afterSongs.stream()
            .map(VotingSongResponse::from)
            .toList();

        return new VotingSongSwipeResponse(currentResponse, beforeResponses, afterResponses);
    }
}
