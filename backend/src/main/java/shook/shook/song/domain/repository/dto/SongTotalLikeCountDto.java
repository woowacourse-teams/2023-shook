package shook.shook.song.domain.repository.dto;

import shook.shook.song.domain.Song;

public interface SongTotalLikeCountDto {

    Song getSong();

    Long getTotalLikeCount();
}
