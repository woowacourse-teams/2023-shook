package shook.shook.voting_song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.voting_song.domain.VotingSong;

@Schema(description = "파트 수집 중인 노래와 이전 노래, 다음 노래 리스트 응답")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class VotingSongSwipeResponse {

    @Schema(description = "요청한 노래 정보", example = "https://www.youtube.com/watch?v=asdfasdfasdf")
    private VotingSongResponse currentSong;

    @Schema(description = "요청한 노래보다 좋아요 수가 많은 노래 리스트")
    private List<VotingSongResponse> prevSongs;

    @Schema(description = "요청한 노래보다 좋아요 수가 적은 노래 리스트")
    private List<VotingSongResponse> nextSongs;

    public static VotingSongSwipeResponse of(
        final List<VotingSong> songs,
        final VotingSong currentSong
    ) {
        final int votingSongIndex = songs.indexOf(currentSong);
        final List<VotingSong> beforeSongs = songs.subList(0, votingSongIndex);
        final List<VotingSong> afterSongs = songs.subList(votingSongIndex + 1, songs.size());

        final VotingSongResponse currentResponse = VotingSongResponse.from(currentSong);
        final List<VotingSongResponse> prevResponses = beforeSongs.stream()
            .map(VotingSongResponse::from)
            .toList();
        final List<VotingSongResponse> nextResponses = afterSongs.stream()
            .map(VotingSongResponse::from)
            .toList();

        return new VotingSongSwipeResponse(currentResponse, prevResponses, nextResponses);
    }
}
