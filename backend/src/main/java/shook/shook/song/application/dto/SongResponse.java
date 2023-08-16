package shook.shook.song.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.member.domain.Member;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SongResponse {

    private final Long id;
    private final String title;
    private final String singer;
    private final int videoLength;
    private final String songVideoId;
    private final String albumCoverUrl;
    private final List<KillingPartResponse> killingParts;

    public static SongResponse of(final Song song, final Member member) {
        return new SongResponse(
            song.getId(),
            song.getTitle(),
            song.getSinger(),
            song.getLength(),
            song.getVideoUrl(),
            song.getAlbumCoverUrl(),
            toKillingPartResponses(song, member)
        );
    }

    public static SongResponse fromUnauthorizedUser(final Song song) {
        return SongResponse.of(song, null);
    }

    private static List<KillingPartResponse> toKillingPartResponses(final Song song,
        final Member member) {
        final List<KillingPart> songKillingParts = song.getLikeCountSortedKillingParts();

        return IntStream.range(0, songKillingParts.size())
            .mapToObj(index ->
                KillingPartResponse.of(song, songKillingParts.get(index), index + 1, member))
            .collect(Collectors.toList());
    }
}
