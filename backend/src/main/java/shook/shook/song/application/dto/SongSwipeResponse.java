package shook.shook.song.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.member.domain.Member;
import shook.shook.song.domain.Song;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SongSwipeResponse {

    private final List<SongResponse> beforeSongs;
    private final SongResponse currentSong;
    private final List<SongResponse> afterSongs;

    public static SongSwipeResponse of(
        final Member member,
        final Song currentSong,
        final List<Song> beforeSongs,
        final List<Song> afterSongs
    ) {
        final SongResponse currentResponse = SongResponse.of(currentSong, member);
        final List<SongResponse> beforeResponses = beforeSongs.stream()
            .map(song -> SongResponse.of(song, member))
            .toList();
        final List<SongResponse> afterResponses = afterSongs.stream()
            .map(song -> SongResponse.of(song, member))
            .toList();

        return new SongSwipeResponse(beforeResponses, currentResponse, afterResponses);
    }

    public static SongSwipeResponse ofUnauthorizedUser(
        final Song currentSong,
        final List<Song> beforeSongs,
        final List<Song> afterSongs
    ) {
        final SongResponse currentResponse = SongResponse.fromUnauthorizedUser(currentSong);
        final List<SongResponse> beforeResponses = beforeSongs.stream()
            .map(SongResponse::fromUnauthorizedUser)
            .toList();
        final List<SongResponse> afterResponses = afterSongs.stream()
            .map(SongResponse::fromUnauthorizedUser)
            .toList();

        return new SongSwipeResponse(beforeResponses, currentResponse, afterResponses);
    }
}
