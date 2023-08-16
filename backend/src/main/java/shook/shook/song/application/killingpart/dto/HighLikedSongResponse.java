package shook.shook.song.application.killingpart.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.repository.dto.SongTotalLikeCountDto;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HighLikedSongResponse {

    private final Long id;
    private final String title;
    private final String singer;
    private final String albumCoverUrl;
    private final long totalLikeCount;

    public static HighLikedSongResponse from(final SongTotalLikeCountDto songTotalVoteCountDto) {
        return new HighLikedSongResponse(
            songTotalVoteCountDto.getSong().getId(),
            songTotalVoteCountDto.getSong().getTitle(),
            songTotalVoteCountDto.getSong().getSinger(),
            songTotalVoteCountDto.getSong().getAlbumCoverUrl(),
            songTotalVoteCountDto.getTotalLikeCount()
        );
    }

    public static List<HighLikedSongResponse> ofSongTotalLikeCounts(final List<SongTotalLikeCountDto> songs) {
        return songs.stream()
            .map(HighLikedSongResponse::from)
            .toList();
    }

}
