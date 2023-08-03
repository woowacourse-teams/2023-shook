package shook.shook.song.domain.repository.dto;

import shook.shook.song.domain.Song;

public interface SongTotalVoteCountDto {

    Song getSong();

    Long getTotalVoteCount();
}
