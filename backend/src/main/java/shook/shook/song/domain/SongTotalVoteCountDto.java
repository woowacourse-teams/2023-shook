package shook.shook.song.domain;

import lombok.Getter;

@Getter
public class SongTotalVoteCountDto {

    private final Song song;
    private final Long totalVoteCount;

    public SongTotalVoteCountDto(final Song song, final Long totalVoteCount) {
        this.song = song;
        this.totalVoteCount = totalVoteCount;
    }
}
