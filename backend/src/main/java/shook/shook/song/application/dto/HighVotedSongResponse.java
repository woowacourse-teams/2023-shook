package shook.shook.song.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.repository.dto.SongTotalVoteCountDto;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HighVotedSongResponse {

    private final Long id;
    private final String title;
    private final String singer;
    private final String imageUrl;
    private final Long totalVoteCount;

    public static HighVotedSongResponse from(final SongTotalVoteCountDto songTotalVoteCountDto) {
        return new HighVotedSongResponse(
            songTotalVoteCountDto.getSong().getId(),
            songTotalVoteCountDto.getSong().getTitle(),
            songTotalVoteCountDto.getSong().getSinger(),
            songTotalVoteCountDto.getSong().getImageUrl(),
            songTotalVoteCountDto.getTotalVoteCount()
        );
    }

    public static List<HighVotedSongResponse> getList(final List<SongTotalVoteCountDto> songs) {
        return songs.stream()
            .map(HighVotedSongResponse::from)
            .toList();
    }
}
