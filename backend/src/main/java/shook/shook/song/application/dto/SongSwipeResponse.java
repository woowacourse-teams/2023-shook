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

    private final List<SongResponse> prevSongs;
    private final SongResponse currentSong;
    private final List<SongResponse> nextSongs;

    public static SongSwipeResponse of(
        final Member member,
        final Song currentSong,
        final List<Song> prevSongs,
        final List<Song> nextSongs
    ) {
        final SongResponse currentResponse = SongResponse.of(currentSong, member);
        final List<SongResponse> prevResponses = prevSongs.stream()
            .map(song -> SongResponse.of(song, member))
            .toList();
        final List<SongResponse> nextResponses = nextSongs.stream()
            .map(song -> SongResponse.of(song, member))
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
