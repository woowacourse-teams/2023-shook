package shook.shook.legacy.song.domain.repository.dto;

import shook.shook.legacy.song.domain.Song;

public interface SongTotalLikeCountDto {

    Song getSong();

    Long getTotalLikeCount();
}
