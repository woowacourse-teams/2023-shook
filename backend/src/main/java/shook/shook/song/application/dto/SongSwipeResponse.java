package shook.shook.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Song;

import java.util.List;

@Schema(description = "첫 스와이프 시, 현재 노래와 이전, 이후 노래 리스트 조회 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SongSwipeResponse {

    @Schema(description = "이전 노래 리스트")
    private final List<SongResponse> prevSongs;

    @Schema(description = "현재 노래")
    private final SongResponse currentSong;

    @Schema(description = "다음 노래 리스트")
    private final List<SongResponse> nextSongs;

    public static SongSwipeResponse of(
        final List<Long> likedKillingPartIds,
        final Song currentSong,
        final List<Song> prevSongs,
        final List<Song> nextSongs
    ) {
        final SongResponse currentResponse = SongResponse.of(currentSong, likedKillingPartIds);
        final List<SongResponse> prevResponses = prevSongs.stream()
            .map(song -> SongResponse.of(song, likedKillingPartIds))
            .toList();
        final List<SongResponse> nextResponses = nextSongs.stream()
            .map(song -> SongResponse.of(song, likedKillingPartIds))
            .toList();

        return new SongSwipeResponse(prevResponses, currentResponse, nextResponses);
    }

    public static SongSwipeResponse ofUnauthorizedUser(
        final Song currentSong,
        final List<Song> prevSongs,
        final List<Song> nextSongs
    ) {
        final SongResponse currentResponse = SongResponse.fromUnauthorizedUser(currentSong);
        final List<SongResponse> prevResponses = prevSongs.stream()
            .map(SongResponse::fromUnauthorizedUser)
            .toList();
        final List<SongResponse> nextResponses = nextSongs.stream()
            .map(SongResponse::fromUnauthorizedUser)
            .toList();

        return new SongSwipeResponse(prevResponses, currentResponse, nextResponses);
    }
}
