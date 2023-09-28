package shook.shook.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;

@Schema(description = "노래 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SongResponse {

    @Schema(description = "노래 id", example = "1")
    private final Long id;

    @Schema(description = "노래 제목", example = "제목")
    private final String title;

    @Schema(description = "가수 이름", example = "가수")
    private final String singer;

    @Schema(description = "노래 비디오 길이", example = "247")
    private final int videoLength;

    @Schema(description = "노래 비디오 id", example = "4")
    private final String songVideoId;

    @Schema(description = "노래 앨범 커버 이미지 url", example = "https://image.com/album-cover.jpg")
    private final String albumCoverUrl;

    @Schema(description = "노래 장르", example = "BALLAD")
    private final String genre;

    @Schema(description = "킬링파트 3개")
    private final List<KillingPartResponse> killingParts;

    public static SongResponse of(final Song song, final List<Long> likedKillingPartIds) {
        return new SongResponse(
            song.getId(),
            song.getTitle(),
            song.getSinger(),
            song.getLength(),
            song.getVideoId(),
            song.getAlbumCoverUrl(),
            song.getGenre().name(),
            toKillingPartResponses(song, likedKillingPartIds)
        );
    }

    public static SongResponse fromUnauthorizedUser(final Song song) {
        return SongResponse.of(song, Collections.emptyList());
    }

    private static List<KillingPartResponse> toKillingPartResponses(final Song song,
                                                                    final List<Long> likedKillingPartIds) {
        final List<KillingPart> songKillingParts = song.getLikeCountSortedKillingParts();

        return IntStream.range(0, songKillingParts.size())
            .mapToObj(index ->
            {
                final KillingPart killingPart = songKillingParts.get(index);
                final int killingPartRank = index + 1;
                return KillingPartResponse.of(
                    song,
                    killingPart,
                    killingPartRank,
                    likedKillingPartIds.contains(killingPart.getId())
                );
            })
            .toList();
    }
}
